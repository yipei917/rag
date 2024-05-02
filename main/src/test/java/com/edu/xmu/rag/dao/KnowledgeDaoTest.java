package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.dao.bo.Knowledge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class KnowledgeDaoTest {
    @Autowired
    KnowledgeDao knowledgeDao;

    @Test
    public void insertTest() {
        Knowledge bo = Knowledge.builder().kbId(111L).code("code").content("content").status(1).title("test").build();
        Knowledge insert = knowledgeDao.insert(bo);
        assertThat(insert.getId() != null).isEqualTo(true);
    }

    @Test
    public void saveTest() {
        Knowledge insert = knowledgeDao.insert(Knowledge.builder().kbId(111L).code("code").content("content").status(1).title("test").build());
        insert.setTitle("test2");
        Knowledge saved = knowledgeDao.save(insert);
        assertThat(saved.getTitle()).isEqualTo("test2");
    }

    @Test
    public void delByIdTest() {
        Knowledge insert = knowledgeDao.insert(Knowledge.builder().kbId(111L).code("code").content("content").status(1).title("test").build());
        Long id = insert.getId();

        knowledgeDao.delById(id);
        Throwable thrown = catchThrowable(() -> knowledgeDao.findKnowledgeById(id));
        assertThat(thrown).isInstanceOf(BusinessException.class);
    }

    @Test
    public void retrieveByKBIdTest() {
        Knowledge bo1 = knowledgeDao.insert(Knowledge.builder().kbId(111L).code("code").content("content").status(1).title("test").build());
        Knowledge bo2 = knowledgeDao.insert(Knowledge.builder().kbId(111L).code("code").content("content").status(1).title("test").build());
        Knowledge bo3 = knowledgeDao.insert(Knowledge.builder().kbId(111L).code("code").content("content").status(1).title("test").build());
        knowledgeDao.insert(bo1);
        knowledgeDao.insert(bo2);
        knowledgeDao.insert(bo3);
        assertThat(3 == knowledgeDao.retrieveByKBId(111L).size()).isEqualTo(true);
    }
}
