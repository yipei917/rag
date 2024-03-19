package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.KnowledgeBaseDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Knowledge {
    @Builder
    public Knowledge(Long id, String code, Long kbId, String title, String content, int status, LocalDateTime gmtCreate, LocalDateTime gmtModified, KnowledgeBaseDao knowledgeBaseDao) {
        this.id = id;
        this.code = code;
        this.kbId = kbId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.knowledgeBaseDao = knowledgeBaseDao;
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
    @ToString.Exclude
    private KnowledgeBase knowledgeBase;

    public KnowledgeBase getKnowledgeBase() {
        if (null == this.kbId) {
            return null;
        }
        if (null == this.knowledgeBase && null != this.knowledgeBaseDao) {
            this.knowledgeBase = knowledgeBaseDao.findUserById(this.kbId);
        }
        return this.knowledgeBase;
    }

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String content;

    @Setter
    @Getter
    private int status;

    @Setter
    @Getter
    private LocalDateTime gmtCreate;

    @Setter
    @Getter
    private LocalDateTime gmtModified;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private KnowledgeBaseDao knowledgeBaseDao;
}
