package com.edu.xmu.rag.dao.bo;

import com.edu.xmu.rag.dao.ChatDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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

    /*
    0：禁用
    1：启用
     */
    @Setter
    @Getter
    private Integer status;

    /*
    0：管理员
    1：用户
     */
    @Setter
    @Getter
    private Integer type;

    @Setter
    @Getter
    private String token;
}
