package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.MainApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MainApplication.class)
public class KnowledgeBasePoMapperTest {
    @Autowired
    private KnowledgeBasePoMapper knowledgeBasePoMapper;

    private void save() {

    }
}
