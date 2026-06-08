package com.example.demo.job;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class ProgressChunkListener {

    @AfterChunk
    public void afterChunk(Chunk<?> chunk) {
        System.out.println("Chunk traité" + chunk.size() + " enregistrements");
    }
}
