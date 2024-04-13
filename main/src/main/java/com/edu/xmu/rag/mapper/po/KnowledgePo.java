package com.edu.xmu.rag.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "knowledge")
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Long kbId;

    private String title;

    private String content;

    private Integer status;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;
}
