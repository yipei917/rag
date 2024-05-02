package com.edu.xmu.rag.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleQuestion {
    private String content;  //  用户输入内容
    private String model;    //  模型名称
    private Long promptId;   //  提示词id
    private Long userId;     //  用户id
    private Long chatId;     //  对话id
    private Integer rag;     //  rag为0时禁用
}
