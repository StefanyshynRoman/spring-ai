package com.stefanyshyn.springai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
 class FileReaderController {
    private final FileReaderService fileReaderService;

    FileReaderController(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }
 @GetMapping("/docx")
    String getResponseFromDoc(){
        return fileReaderService.getResponseFromDocx();
 }
    @GetMapping("/docx")
    String getResponseFromDoc(){
        return fileReaderService.getResponseFromDocx();
    }
}
