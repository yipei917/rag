package com.edu.xmu.rag.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "knowledge_base")
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBasePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String description;

    private Integer status;

    private Long userId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;
}
