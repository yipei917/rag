package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.dao.bo.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class MessageDaoTest {
    @Autowired
    MessageDao messageDao;

    @Test
    public void insertTest() {
        Message bo = new Message().builder().chatId(1L).role(0).content("test").build();
        Message insert = messageDao.insert(bo);
        assertThat(insert.getId() != null);
    }

    @Test
    public void retrieveByChatId() {
        Message bo1 = new Message().builder().chatId(111L).role(0).content("test1").build();
        Message bo2 = new Message().builder().chatId(111L).role(1).content("test2").build();
        Message bo3 = new Message().builder().chatId(111L).role(2).content("test3").build();
        messageDao.insert(bo1);
        messageDao.insert(bo2);
        messageDao.insert(bo3);

        List<Message> list = messageDao.retrieveByChatId(111L);
        assertThat(list.size() == 3);
    }

    @Test
    public void delList() {
        Message bo1 = new Message().builder().chatId(111L).role(0).content("test1").build();
        Message bo2 = new Message().builder().chatId(111L).role(1).content("test2").build();
        Message bo3 = new Message().builder().chatId(111L).role(2).content("test3").build();
        messageDao.insert(bo1);
        messageDao.insert(bo2);
        messageDao.insert(bo3);

        messageDao.delList(111L);
        List<Message> list = messageDao.retrieveByChatId(111L);
        assertThat(list.size() == 3);
    }
}
