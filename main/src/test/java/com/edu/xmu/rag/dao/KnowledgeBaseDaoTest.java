package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
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
public class KnowledgeBaseDaoTest {
    @Autowired
    KnowledgeBaseDao knowledgeBaseDao;

    @Test
    public void insertTest() {
        KnowledgeBase bo = KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build();
        KnowledgeBase insert = knowledgeBaseDao.insert(bo);
        System.out.println(bo.getId());
        assertThat(null != insert.getId()).isEqualTo(true);
    }

    @Test
    public void saveTest() {
        KnowledgeBase insert = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        insert.setTitle("test2");
        KnowledgeBase saved = knowledgeBaseDao.save(insert);
        assertThat(saved.getTitle()).isEqualTo("test2");
    }

    @Test
    public void delByIdTest() {
        KnowledgeBase insert = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        Long id = insert.getId();

        knowledgeBaseDao.delById(id);
        Throwable thrown = catchThrowable(() -> knowledgeBaseDao.findKnowledgeBaseById(id));
        assertThat(thrown).isInstanceOf(BusinessException.class);
    }

    @Test
    public void retrieveByUserIdTest() {
        KnowledgeBase bo1 = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        KnowledgeBase bo2 = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        KnowledgeBase bo3 = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        knowledgeBaseDao.insert(bo1);
        knowledgeBaseDao.insert(bo2);
        knowledgeBaseDao.insert(bo3);

        assertThat(3 == knowledgeBaseDao.retrieveByUserId(111L).size()).isEqualTo(true);
    }

    @Test
    public void retrieveAllTest() {
        KnowledgeBase bo1 = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        KnowledgeBase bo2 = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        KnowledgeBase bo3 = knowledgeBaseDao.insert(KnowledgeBase.builder().title("test").code("code").userId(111L).status(1).build());
        knowledgeBaseDao.insert(bo1);
        knowledgeBaseDao.insert(bo2);
        knowledgeBaseDao.insert(bo3);

        assertThat(5 == knowledgeBaseDao.retrieveAll().size()).isEqualTo(true);
    }
}
