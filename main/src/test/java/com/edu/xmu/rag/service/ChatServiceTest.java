package com.edu.xmu.rag.service;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.controller.vo.ChatVo;
import com.edu.xmu.rag.controller.vo.SimpleQuestion;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.ChatDao;
import com.edu.xmu.rag.dao.bo.Chat;
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
public class ChatServiceTest {
    @Autowired
    ChatService chatService;

    @Autowired
    ChatDao chatDao;

    @Test
    public void findMessageListByChatIdTest() {
        assertThat(chatService.findMessageListByChatId(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void findChatListByUserIdTest() {
        assertThat(chatService.findChatListByUserId(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void createChatTest() {
        Chat vo = new Chat();
        vo.setTitle("test");
        vo.setUserId(1L);
        assertThat(chatService.createChat(vo).getCode()).isEqualTo(ReturnNo.CREATED);
    }

    @Test
    public void updateChatTest() {
        ChatVo vo = cloneObj(chatDao.findChatById(1L), ChatVo.class);
        vo.setTitle("test2");
        assertThat(chatService.updateChat(vo).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void delChatTest() {
        assertThat(chatService.delChat(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void chatTest1() {
        SimpleQuestion question = new SimpleQuestion();
        question.setRag(0);
        question.setContent("请给一段c++");
        question.setUserId(1L);
        question.setChatId(1L);

        assertThat(chatService.chat(question).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void chatTest2() {
        SimpleQuestion question = new SimpleQuestion();
        question.setRag(0);
        question.setUserId(1L);
        question.setChatId(1L);

        assertThat(chatService.chat(question).getCode()).isEqualTo(ReturnNo.CHAT_WRONG);
    }
}
