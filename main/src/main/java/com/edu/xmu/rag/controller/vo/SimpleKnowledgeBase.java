package com.edu.xmu.rag.controller.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleKnowledgeBase {
    @NotBlank(message = "编码不能为空")
    private String code;
    private String description;
    @NotBlank(message = "用户Id不能为空")
    private Long userId;
}
