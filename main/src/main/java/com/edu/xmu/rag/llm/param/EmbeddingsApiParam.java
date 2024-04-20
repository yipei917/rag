package com.edu.xmu.rag.llm.param;

import org.springframework.stereotype.Component;

@Component
public class EmbeddingsApiParam {
    private String model = "text-embedding-ada-002";
    private String input;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
