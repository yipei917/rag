package com.edu.xmu.rag.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "prompt")
@NoArgsConstructor
@AllArgsConstructor
public class PromptPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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