package com.edu.xmu.rag.dao.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    @Builder
    public User(Long id, String name, String password, Integer status, Integer type, String token) {
        this.id = id;
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
    private String name;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private Integer status;

    @Setter
    @Getter
    private Integer type;

    @Setter
    @Getter
    private String token;
}
