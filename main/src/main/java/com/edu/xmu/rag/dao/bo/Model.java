package com.edu.xmu.rag.dao.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Model implements Serializable {
    @Builder
    public Model(Long id, String code, String name, String description, int status, String product, Long rule, String url) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.status = status;
        this.product = product;
        this.rule = rule;
        this.url = url;
    }

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String code;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private int status;

    @Setter
    @Getter
    private String product;

    @Setter
    @Getter
    private Long rule;

    @Setter
    @Getter
    private String url;
}
