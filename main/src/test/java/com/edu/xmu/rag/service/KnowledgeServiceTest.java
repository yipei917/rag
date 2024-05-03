package com.edu.xmu.rag.service;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.controller.vo.SimpleKnowledge;
import com.edu.xmu.rag.controller.vo.SimpleKnowledgeBase;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.KnowledgeDao;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.edu.xmu.rag.core.util.Common.cloneObj;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class KnowledgeServiceTest {
    @Autowired
    KnowledgeService knowledgeService;

    @Autowired
    KnowledgeDao knowledgeDao;

    @Test
    public void createKnowledgeBaseTest() {
        SimpleKnowledgeBase vo = new SimpleKnowledgeBase();
        vo.setTitle("test");
        vo.setCode("code");
        vo.setUserId(111L);

        assertThat(knowledgeService.createKnowledgeBase(vo).getCode()).isEqualTo(ReturnNo.CREATED);
    }

    @Test
    public void updateKnowledgeBaseTest() {
        SimpleKnowledgeBase vo = new SimpleKnowledgeBase();
        vo.setTitle("test");
        vo.setCode("code");
        vo.setUserId(111L);
        KnowledgeBase insert = (KnowledgeBase) knowledgeService.createKnowledgeBase(vo).getData();
        insert.setTitle("test2");
        SimpleKnowledgeBase simple = cloneObj(insert, SimpleKnowledgeBase.class);
        assertThat(knowledgeService.updateKnowledgeBase(simple).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void enableKnowledgeBaseTest() {
        assertThat(knowledgeService.enableKnowledgeBase(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void disableKnowledgeBaseTest() {
        assertThat(knowledgeService.disableKnowledgeBase(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseCodeTest() {
        assertThat(knowledgeService.findKnowledgeBaseCode(1L)).isEqualTo("qq1");
    }

    @Test
    public void findKnowledgeBaseCodeByUserIdTest() {
        assertThat(knowledgeService.findKnowledgeBaseCodeByUserId(1L).size() == 2).isEqualTo(true);
    }

    @Test
    public void isCodeExistTest1() {
        assertThat(knowledgeService.isCodeExist("qq", 1L)).isEqualTo(true);
    }

    @Test
    public void isCodeExistTest2() {
        assertThat(knowledgeService.isCodeExist("qqq", 1L)).isEqualTo(false);
    }

    @Test
    public void delKnowledgeBaseTest() {
        assertThat(knowledgeService.delKnowledge(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest1() {
        assertThat(knowledgeService.findKnowledgeBase(0L, "*").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest2() {
        assertThat(knowledgeService.findKnowledgeBase(1L, "*").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest3() {
        assertThat(knowledgeService.findKnowledgeBase(0L, "qq").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest4() {
        assertThat(knowledgeService.findKnowledgeBase(1L, "qq").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void createKnowledgeTest() {
        SimpleKnowledge vo = new SimpleKnowledge();
        vo.setKbId(1L);
        vo.setTitle("test");
        vo.setCode("code");
        assertThat(knowledgeService.createKnowledge(vo).getCode()).isEqualTo(ReturnNo.CREATED);
    }

    @Test
    public void updateKnowledgeTest() {
        SimpleKnowledge vo = cloneObj(knowledgeDao.findKnowledgeById(1L), SimpleKnowledge.class);
        vo.setTitle("test2");
        assertThat(knowledgeService.updateKnowledge(vo).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void enableKnowledgeTest() {
        assertThat(knowledgeService.enableKnowledge(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void disableKnowledgeTest() {
        assertThat(knowledgeService.disableKnowledge(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void delKnowledgeTest() {
        assertThat(knowledgeService.delKnowledge(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest1() {
        assertThat(knowledgeService.findKnowledge(1L, null, null).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest2() {
        assertThat(knowledgeService.findKnowledge(1L, "code", null).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest3() {
        assertThat(knowledgeService.findKnowledge(1L, null, "rag").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest4() {
        assertThat(knowledgeService.findKnowledge(1L, "code", "rag").getCode()).isEqualTo(ReturnNo.OK);
    }
}
