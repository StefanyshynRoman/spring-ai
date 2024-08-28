package com.stefanyshyn.springai;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileReaderService {
    private final ChatModel chatModel;

    public FileReaderService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    String getResponseFromDocx() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader("classpath:firma.docx");
        String userPrompt = "Jaka jest wizja i misja firmy?";
        Message userMessage = new UserMessage(userPrompt);
        String systemPrompt = tikaDocumentReader.get().stream()
                .map(Document::getContent)
                .collect(Collectors.joining());
        Message systemMessage = new SystemPromptTemplate(systemPrompt)
                .createMessage();

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }

    String getResponseFromPdf() {
        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                        .withNumberOfBottomTextLinesToDelete(0)
                        .build())
                .withPagesPerDocument(1)
                .build();
        PagePdfDocumentReader padePdfDocumentReader = new PagePdfDocumentReader("class:firma.pdf", config);
        String userPrompt = "Jaka jest wizja i misja firmy?";
        Message userMessage = new UserMessage(userPrompt);
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
        List<Document> documents = tokenTextSplitter.apply(padePdfDocumentReader.get());

        String systemPrompt = """
                Dokumentacja: {documents}
                """;
        Message systemMessage = new SystemPromptTemplate(systemPrompt)
                .createMessage(Map.of("documents", documents));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }
}
