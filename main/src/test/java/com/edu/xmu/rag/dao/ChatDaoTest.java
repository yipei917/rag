package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.Chat;
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
public class ChatDaoTest {
    @Autowired
    ChatDao chatDao;

    @Test
    public void insertTest() {
        Chat bo = Chat.builder().userId(111L).title("test1").build();
        Chat insert = chatDao.insert(bo);
        assertThat(null != insert.getId());
    }

    @Test
    public void saveTest() {
        Chat chat = chatDao.findChatById(1L);
        chat.setTitle("test2");
        Chat saved = chatDao.save(chat);
        assertThat(saved.getTitle()).isEqualTo("test2");
    }

    @Test
    public void retrieveByUserIdTest() {
        Chat bo1 = Chat.builder().userId(111L).title("test1").build();
        Chat bo2 = Chat.builder().userId(111L).title("test2").build();
        Chat bo3 = Chat.builder().userId(111L).title("test3").build();
        chatDao.insert(bo1);
        chatDao.insert(bo2);
        chatDao.insert(bo3);

        assertThat(3 == chatDao.retrieveByUserId(111L).size());
    }

    @Test
    public void delByIdTest() {
        Chat insert = chatDao.insert(Chat.builder().userId(111L).title("test1").build());
        Long id = insert.getId();

        chatDao.delById(id);
        Throwable thrown = catchThrowable(() -> {
            chatDao.findChatById(id);
        });
        assertThat(thrown).isInstanceOf(BusinessException.class);
    }
}
