package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.ChatDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Message implements Serializable {
    @Builder
    public Message(Long id, int role, String content, Long chatId, LocalDateTime gmtCreate, ChatDao chatDao) {
        this.id = id;
        this.role = role;
        this.content = content;
        this.chatId = chatId;
        this.gmtCreate = gmtCreate;
        this.chatDao = chatDao;
    }

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private int role;

    @Setter
    @Getter
    private String content;

    @Setter
    @Getter
    private Long chatId;

    @Setter
    @ToString.Exclude
    private Chat chat;

//    public Chat getChat() {
//        if (null == this.getChat()) {
//            return null;
//        }
//        if (null == this.chat && null != this.chatDao) {
//            this.chat = this.chatDao.findChatById(this.chatId);
//        }
//        return this.chat;
//    }

    @Setter
    @Getter
    private LocalDateTime gmtCreate;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ChatDao chatDao;
}
