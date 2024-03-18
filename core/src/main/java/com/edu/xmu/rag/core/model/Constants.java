package com.edu.xmu.rag.core.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* 常量定义 */
public interface Constants {
    /**
     * 时间格式定义
     */
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd 'T' HH:mm:ss");

    LocalDateTime END_TIME = LocalDateTime.parse("2099-12-12 T 23:59:59", DATE_TIME_FORMATTER);

    LocalDateTime BEGIN_TIME = LocalDateTime.parse("2000-12-12 T 23:59:59", DATE_TIME_FORMATTER);

    /**
     * 查询结果最大返回值
     */
    int MAX_RETURN = 1000;

    public static final Long IDNOTEXIST = -1L;

}
