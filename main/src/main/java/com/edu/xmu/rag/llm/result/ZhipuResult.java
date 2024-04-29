package com.edu.xmu.rag.llm.result;

import lombok.Data;

@Data
public class ZhipuResult {
    private int code;
    private String msg;
    private boolean success;
    private ZhipuEmbeddingResult data;
}
