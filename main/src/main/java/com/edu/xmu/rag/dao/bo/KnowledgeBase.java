package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.KnowledgeDao;
import com.edu.xmu.rag.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class KnowledgeBase implements Serializable {
    @Builder
    public KnowledgeBase(Long id,String title, String code, String description, Integer status, Long userId, LocalDateTime gmtCreate, LocalDateTime gmtModified, KnowledgeDao knowledgeDao) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.description = description;
        this.status = status;
        this.userId = userId;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.knowledgeDao = knowledgeDao;
    }

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String code;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Integer status;

    @Setter
    @Getter
    private Long userId;

    @JsonIgnore
    @ToString.Exclude
    private List<Knowledge> knowledgeList;

    public List<Knowledge> getKnowledgeList() {
        if (null == this.knowledgeList && null != this.knowledgeDao) {
            this.knowledgeList = knowledgeDao.retrieveByKBId(this.id);
        }
        return this.knowledgeList;
    }

    @Setter
    @Getter
    private LocalDateTime gmtCreate;

    @Setter
    @Getter
    private LocalDateTime gmtModified;


    @Setter
    @JsonIgnore
    @ToString.Exclude
    private KnowledgeDao knowledgeDao;
}
