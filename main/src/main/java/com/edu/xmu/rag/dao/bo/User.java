package com.edu.xmu.rag.dao.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Builder
    public User(Long id, Long account, String name, String password, int status, int type, String token) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.password = password;
        this.status = status;
        this.type = type;
        this.token = token;
    }

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private Long account;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private int status;

    @Setter
    @Getter
    private int type;

    @Setter
    @Getter
    private String token;
}
