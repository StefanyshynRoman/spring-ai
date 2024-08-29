package com.stefanyshyn.springai;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.observation.EmbeddingModelObservationContext;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {
    @Bean
    VectorStore simpleVectorStore(EmbeddingModel embeddingModel){
        return new SimpleVectorStore(embeddingModel);
    }
}
