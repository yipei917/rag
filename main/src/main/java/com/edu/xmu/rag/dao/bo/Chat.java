package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Chat implements Serializable {
    @Builder
    public Chat(Long id, String title, Long userId, UserDao userDao) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.userDao = userDao;
    }

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String title;

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
            this.user  = userDao.findUserById(this.userId);
        }
        return this.user;
    }

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private UserDao userDao;
}
