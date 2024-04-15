package com.edu.xmu.rag.controller.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleKnowledge {
    @NotBlank(message = "编码不能为空")
    private String code;
    @NotBlank(message = "知识库Id不能为空")
    private Long kbId;
    @NotBlank(message = "标题不能为空")
    private String title;
    private String content;
}
