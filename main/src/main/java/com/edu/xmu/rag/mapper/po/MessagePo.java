package com.edu.xmu.rag.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
public class MessagePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer role;

    private String content;

    private Long chatId;

    private LocalDateTime gmtCreate;
}
