package com.stefanyshyn.springai;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.stereotype.Service;

@Service
 class ImageService {
    private final ImageModel imageModel;

    ImageService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
    String getPictureUrl(){
        ImageOptions imageOptions= OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .withModel(OpenAiImageApi.ImageModel.DALL_E_2.getValue())
                .build();
        ImagePrompt imagePrompt = new ImagePrompt("Paint programmer in dark forest", imageOptions);
        return  imageModel.call(imagePrompt)
                .getResult()
                .getOutput()
                .getUrl();
    }
}
