package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import com.edu.xmu.rag.mapper.KnowledgeBasePoMapper;
import com.edu.xmu.rag.mapper.po.KnowledgeBasePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KnowledgeBaseDao {
    private KnowledgeBasePoMapper knowledgeBasePoMapper;

    @Autowired
    KnowledgeBaseDao(KnowledgeBasePoMapper knowledgeBasePoMapper) {
        this.knowledgeBasePoMapper = knowledgeBasePoMapper;
    }
}
