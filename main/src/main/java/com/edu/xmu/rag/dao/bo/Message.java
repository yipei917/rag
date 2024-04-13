package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.ChatDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {
    @Builder
    public Message(Long id, Integer role, String content, Long chatId, LocalDateTime gmtCreate) {
        this.id = id;
        this.role = role;
        this.content = content;
        this.chatId = chatId;
        this.gmtCreate = gmtCreate;
    }

    @Setter
    @Getter
    private Long id;

    /*
    0：system
    1：assistant
    2：user
     */
    @Setter
    @Getter
    private Integer role;

    @Setter
    @Getter
    private String content;

    @Setter
    @Getter
    private Long chatId;

    @Setter
    @Getter
    private LocalDateTime gmtCreate;
}
