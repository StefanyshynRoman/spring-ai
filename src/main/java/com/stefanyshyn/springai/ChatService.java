package com.stefanyshyn.springai;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

@Service
class ChatService {
    private final ChatModel chatModel;

    ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    String getJoke() {
        return chatModel.call("Opowiedz zart o programistach");


    }

    String getNobelWinners() {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withTemperature(0.8F)
                .withModel(OpenAiApi.ChatModel.GPT_3_5_TURBO)
                .build();
        String promptString = """
                Write 5 ukrainian Nobel laureate with year 
                """;
        Prompt prompt = new Prompt(promptString, chatOptions);
        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getContent();
    }

    StoryDto getStory() {
        String promptStory = """  
                Napisz po polsku historię z gatunku {gatunek} na temat: {temat},     {format}   
                  """;
        BeanOutputConverter<StoryDto> storyDtoBeanOutputConverter = new BeanOutputConverter<>(StoryDto.class);
        PromptTemplate promptTemplate = new PromptTemplate(promptStory);
        promptTemplate.add("gatunek", "horror");
        promptTemplate.add("temat", "chomik");
        promptTemplate.add("format", storyDtoBeanOutputConverter.getFormat());
        String content = chatModel.call(promptTemplate.create())
                .getResult()
                .getOutput()
                .getContent();
        return storyDtoBeanOutputConverter.convert(content);
    }
}
