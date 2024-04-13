package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class KnowledgeBase implements Serializable {
    @Builder
    public KnowledgeBase(Long id, String code, String description, Integer status, Long userId, LocalDateTime gmtCreate, LocalDateTime gmtModified, UserDao userDao) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.status = status;
        this.userId = userId;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.userDao = userDao;
    }

    @Setter
    @Getter
    private Long id;

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

    @Setter
    @ToString.Exclude
    private User user;

    public User getUser() {
        if (null == this.userId) {
            return null;
        }
        if (null == this.user && null != this.userDao) {
            this.user = userDao.findUserById(this.userId);
        }
        return this.user;
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
    private UserDao userDao;
}
