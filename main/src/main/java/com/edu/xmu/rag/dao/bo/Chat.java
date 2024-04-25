package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.MessageDao;
import com.edu.xmu.rag.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chat implements Serializable {
    @Builder
    public Chat(Long id, String title, Long userId, User user, List<Message> messageList, UserDao userDao, MessageDao messageDao) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.user = user;
        this.messageList = messageList;
        this.userDao = userDao;
        this.messageDao = messageDao;
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
        if (null == this.userId) { // 如果外键为空，返回空
            return null;
        }
        if (null == this.user && null != this.userDao) { // 外键不为空，根据外键获取对象
            this.user  = userDao.findUserById(this.userId);
        }
        return this.user;
    }

    @JsonIgnore
    @ToString.Exclude
    private List<Message> messageList;

    public List<Message> getMessageList() {
        if (null == this.messageList && null != this.messageDao) {
            this.messageList = messageDao.retrieveByChatId(this.id);
        }
        return this.messageList;
    }

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private UserDao userDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private MessageDao messageDao;
}
