package com.edu.xmu.rag.dao.bo;

import java.util.List;

public class ChatData {
    private Long id;
    private String content;
    private Integer contentWordCount;

    public ChatData(Long id, String content, Integer contentWordCount) {
        this.id = id;
        this.content = content;
        this.contentWordCount = contentWordCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentWordCount() {
        return contentWordCount;
    }

    public void setContentWordCount(Integer contentWordCount) {
        this.contentWordCount = contentWordCount;
    }

}
