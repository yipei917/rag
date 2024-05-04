package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.controller.vo.SimpleKnowledge;
import com.edu.xmu.rag.controller.vo.SimpleKnowledgeBase;
import com.edu.xmu.rag.controller.vo.SimpleSearch;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.KnowledgeBaseDao;
import com.edu.xmu.rag.dao.KnowledgeDao;
import com.edu.xmu.rag.dao.bo.Knowledge;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.edu.xmu.rag.core.util.Common.cloneObj;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class KnowledgeControllerTest {
    @Autowired
    KnowledgeController knowledgeController;

    @Autowired
    KnowledgeBaseDao knowledgeBaseDao;

    @Autowired
    KnowledgeDao knowledgeDao;

    @Test
    public void createKnowledgeBaseTest1() {
        SimpleKnowledgeBase vo = new SimpleKnowledgeBase();
        vo.setTitle("test");
        vo.setCode("test");
        vo.setUserId(1L);

        assertThat(knowledgeController.createKnowledgeBase(vo).getCode()).isEqualTo(ReturnNo.CODE_EXIST);
    }

    @Test
    public void createKnowledgeBaseTest2() {
        SimpleKnowledgeBase vo = new SimpleKnowledgeBase();
        vo.setTitle("test2");
        vo.setCode("test");
        vo.setUserId(1L);

        assertThat(knowledgeController.createKnowledgeBase(vo).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void updateKnowledgeBaseTest() {
        KnowledgeBase bo = knowledgeBaseDao.findKnowledgeBaseById(1L);
        bo.setTitle("test2");
        SimpleKnowledgeBase vo = cloneObj(bo, SimpleKnowledgeBase.class);
        assertThat(knowledgeController.updateKnowledgeBase(vo).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void delKnowledgeBaseTest() {
        assertThat(knowledgeController.delKnowledgeBase(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void changeKnowledgeBaseStatusTest1() {
        assertThat(knowledgeController.changeKnowledgeBaseStatus(1L, 1).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void changeKnowledgeBaseStatusTest2() {
        assertThat(knowledgeController.changeKnowledgeBaseStatus(1L, 0).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest1() {
        assertThat(knowledgeController.findKnowledgeBase(0L, "*").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest2() {
        assertThat(knowledgeController.findKnowledgeBase(1L, "*").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest3() {
        assertThat(knowledgeController.findKnowledgeBase(0L, "qq").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeBaseTest4() {
        assertThat(knowledgeController.findKnowledgeBase(1L, "qq").getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void createKnowledgeTest() {
        SimpleKnowledge vo = new SimpleKnowledge();
        vo.setKbId(1L);
        vo.setTitle("test");
        vo.setCode("code");
        vo.setContent("这是一段测试。");

        assertThat(knowledgeController.createKnowledge(vo).getCode()).isEqualTo(ReturnNo.CREATED);
    }

    @Test
    public void updateKnowledgeTest() {
        Knowledge bo = knowledgeDao.findKnowledgeById(1L);
        bo.setTitle("test2");
        SimpleKnowledge vo = cloneObj(bo, SimpleKnowledge.class);
        assertThat(knowledgeController.updateKnowledge(vo).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void delKnowledgeTest() {
        assertThat(knowledgeController.delKnowledge(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void changeKnowledgeStatusTest1() {
        assertThat(knowledgeController.changeKnowledgeStatus(1L, 0).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void changeKnowledgeStatusTest2() {
        assertThat(knowledgeController.changeKnowledgeStatus(1L, 1).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest1() {
        SimpleSearch search = new SimpleSearch();
        search.setId(1L);
        assertThat(knowledgeController.findKnowledge(search).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest2() {
        SimpleSearch search = new SimpleSearch();
        search.setId(1L);
        search.setCode("code");
        assertThat(knowledgeController.findKnowledge(search).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest3() {
        SimpleSearch search = new SimpleSearch();
        search.setId(1L);
        search.setKeyword("rag");
        assertThat(knowledgeController.findKnowledge(search).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findKnowledgeTest4() {
        SimpleSearch search = new SimpleSearch();
        search.setId(1L);
        search.setCode("code");
        search.setKeyword("rag");
        assertThat(knowledgeController.findKnowledge(search).getCode()).isEqualTo(ReturnNo.OK);
    }
}
