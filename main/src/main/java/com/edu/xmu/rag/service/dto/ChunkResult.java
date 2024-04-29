package com.edu.xmu.rag.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChunkResult {
    private String docId;
    private int chunkId;
    private String content;

}
