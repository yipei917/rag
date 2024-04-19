package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void insertTest() {
        new User();
        User user = User.builder()
                .name("test")
                .password("password")
                .status(0)
                .type(0).build();

        User saved = userDao.insert(user);
    }

    @Test
    public void findUserByIdTest1() {
        User user = User.builder()
                .name("test")
                .password("password")
                .status(0)
                .type(0).build();
        user = userDao.insert(user);

        User obj = userDao.findUserById(user.getId());
        assertThat(user.getId()).isEqualTo(obj.getId());
    }

    @Test
    public void findUserByIdTest2() {
        assertThrows(BusinessException.class, ()-> userDao.findUserById(5000L));
    }

    @Test
    public void saveTest() {
        User user = User.builder()
                .name("test")
                .password("password")
                .status(0)
                .type(0).build();
        user = userDao.insert(user);

        user.setType(1);
        User saved = userDao.save(user);
        assertThat(saved.getType()).isEqualTo(1);
    }

    @Test
    public void delByIdTest() {
        User user = User.builder()
                .name("test")
                .password("password")
                .status(0)
                .type(0).build();
        user = userDao.insert(user);
        ReturnObject ret = userDao.delById(user);
        assertThat(ret.getCode()).isEqualTo(ReturnNo.OK);
    }
}
