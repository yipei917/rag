package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import com.edu.xmu.rag.mapper.po.KnowledgeBasePo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.edu.xmu.rag.core.util.Common.putGmtFields;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class KnowledgeBasePoMapperTest {
    @Autowired
    private KnowledgeBasePoMapper knowledgeBasePoMapper;

    @Test
    public void save() {
        KnowledgeBasePo po = new KnowledgeBasePo(null,"qq","11",1,11L,null,null);
        putGmtFields(po, "create");
        knowledgeBasePoMapper.save(po);
    }
}
