package com.edu.xmu.rag.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptVo {

    private Long id;

    private String name;

    private String code;

    private Integer status;

    private String systemPrompt;

    private String userPrompt;

    private Long modelId;

    private Long userId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
