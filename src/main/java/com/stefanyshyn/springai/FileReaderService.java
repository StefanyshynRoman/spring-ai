package com.stefanyshyn.springai;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;

import java.util.List;
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

        Prompt prompt=new Prompt(List.of(userMessage, systemMessage));
        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }
}
