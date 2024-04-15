package com.edu.xmu.rag.dao.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Knowledge implements Serializable {
    @Builder
    public Knowledge(Long id, String code, Long kbId, String title, String content, Integer status, LocalDateTime gmtCreate, LocalDateTime gmtModified) {
        this.id = id;
        this.code = code;
        this.kbId = kbId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String code;

    @Setter
    @Getter
    private Long kbId;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String content;

    @Setter
    @Getter
    private Integer status;

    @Setter
    @Getter
    private LocalDateTime gmtCreate;

    @Setter
    @Getter
    private LocalDateTime gmtModified;
}
