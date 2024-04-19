package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.ChatDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    public Message(String content, String role) {
        this.setContent(content);
        this.setRole(role);
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

    public void setRole(String role) {
        this.role = ROLE_NUM.get(role);
    }

    @ToString.Exclude
    @JsonIgnore
    public static final Map<String, Integer> ROLE_NUM = new HashMap<>() {
        {
            put("system", 0);
            put("assistant", 1);
            put("user", 2);
        }
    };

    @Setter
    @Getter
    private String content;

    @Setter
    @Getter
    private Long chatId;

    @Setter
    @Getter
    private LocalDateTime gmtCreate;

    @Override
    public String toString() {
        return "content = " + getContent() + ", role = " +  getRole();
    }
}
