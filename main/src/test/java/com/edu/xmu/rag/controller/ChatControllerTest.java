package com.edu.xmu.rag.controller;

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
public class ChatControllerTest {
    @Autowired
    ChatController chatController;

    @Autowired
    ChatDao chatDao;

    @Test
    public void getMessagesTest() {
        assertThat(chatController.getMessages(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void getChatListTest() {
        assertThat(chatController.getChatList(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void createChatTest() {
        Chat vo = new Chat();
        vo.setTitle("test");
        vo.setUserId(1L);
        assertThat(chatController.createChat(vo).getCode()).isEqualTo(ReturnNo.CREATED);
    }

    @Test
    public void updateChatTest() {
        ChatVo vo = cloneObj(chatDao.findChatById(1L), ChatVo.class);
        vo.setTitle("test2");
        assertThat(chatController.updateChat(vo).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void delChatTest() {
        assertThat(chatController.delChat(1L).getCode()).isEqualTo(ReturnNo.OK);
    }

    @Test
    public void chatTest() {
        SimpleQuestion question = new SimpleQuestion();
        question.setRag(0);
        question.setUserId(1L);
        question.setChatId(1L);

        assertThat(chatController.chat(question).getCode()).isEqualTo(ReturnNo.CHAT_WRONG);
    }
}
