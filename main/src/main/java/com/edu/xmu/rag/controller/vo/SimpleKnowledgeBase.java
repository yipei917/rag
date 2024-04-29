package com.edu.xmu.rag.controller.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleKnowledgeBase {
    private Long id;
    private String code;
    private String description;
    private String title;
    private Long userId;
}
