package com.edu.xmu.rag.service.dto;

import lombok.Data;

@Data
public class ChunkResult {
    private String docId;
    private int chunkId;
    private String content;

}
