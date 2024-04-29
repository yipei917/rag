package com.edu.xmu.rag.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleQuestion {
    private String content;
    private String model;
    private Long promptId;
    private Long userId;
    private Long chatId;
    private Integer rag;
}
