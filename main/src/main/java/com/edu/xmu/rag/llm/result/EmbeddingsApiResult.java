package com.edu.xmu.rag.llm.result;

import com.google.api.Usage;

import java.util.List;


public class EmbeddingsApiResult {
    private String object;
    private List<EmbeddingObj> data;
    private String model;
    private Usage usage;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<EmbeddingObj> getData() {
        return data;
    }

    public void setData(List<EmbeddingObj> data) {
        this.data = data;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}
