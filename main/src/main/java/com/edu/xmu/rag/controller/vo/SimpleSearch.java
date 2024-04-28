package com.edu.xmu.rag.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSearch {
    private Long id;
    private String code;
    private String keyword;
}
