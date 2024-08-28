package com.stefanyshyn.springai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
class ChatController {
    private final ChatService chatService;

    ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/joke")
    String getJoke() {
        return chatService.getJoke();
    }

    @GetMapping("/nobbel-winners")
    String getNobelWinners() {
        return chatService.getNobelWinners();
    }
    @GetMapping("/story")
    StoryDto getStory(){
        return chatService.getStory();
    }
}
