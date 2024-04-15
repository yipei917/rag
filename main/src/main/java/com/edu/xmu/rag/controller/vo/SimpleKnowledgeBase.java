package com.edu.xmu.rag.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleKnowledgeBase {
    private String code;
    private String description;
    private Long userId;
}
