package com.edu.xmu.rag.controller.vo;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeVo {
    private Long id;
    private String code;
    private String title;
    private Long kbId;
    private String content;
    private Integer status;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
