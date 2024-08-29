package com.stefanyshyn.springai;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/api")
class VectorStoreController {
    private final VectorStoreService vectorStoreService;

    VectorStoreController(VectorStoreService vectorStoreService) {
        this.vectorStoreService = vectorStoreService;
    }

    @GetMapping("/add-data")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addDataToVectorStore() {
        vectorStoreService.loadDocumentsToVectorStore();
    }

    @GetMapping("/test-vector-store")
    String testVectorStore() {
        return vectorStoreService.testVectorDb();
    }
}
