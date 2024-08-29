package com.stefanyshyn.springai;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VectorStoreService {
    private final ChatModel chatModel;
    private final VectorStore simpleVectorStore;

    public VectorStoreService(ChatModel chatModel, VectorStore simpleVectorStore) {
        this.chatModel = chatModel;
        this.simpleVectorStore = simpleVectorStore;
    }

    String testVectorDb() {
        String userPrompt = "Jaka jest wizja i misja firmy?";
        Message userMessage = new UserMessage(userPrompt);


        String systemPrompt = """
                Dokumentacja: {documents}
                """;

        List<Document> similarDocuments = simpleVectorStore.similaritySearch(
                SearchRequest.query(userPrompt)
                        .withTopK(2));

        Message systemMessage = new SystemPromptTemplate(systemPrompt)
                .createMessage(Map.of("documents", similarDocuments));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }

    void loadDocumentsToVectorStore()

    {
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader("classpath:firma.pdf");
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
        List<Document> documents = tokenTextSplitter.apply(pagePdfDocumentReader.get());
        simpleVectorStore.add(documents);
    }
}
