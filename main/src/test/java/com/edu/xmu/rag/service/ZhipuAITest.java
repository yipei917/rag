package com.edu.xmu.rag.service;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.controller.vo.SimpleQuestion;
import com.edu.xmu.rag.core.exception.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class ZhipuAITest {
    private static final Logger logger = LoggerFactory.getLogger(ZhipuAITest.class);

    @Autowired
    ZhipuAI zhipuAI;

    @Test
    public void chatTest1() throws Exception {
        SimpleQuestion question = new SimpleQuestion();
        question.setRag(0);
        question.setContent("请给一段c++");
        question.setUserId(1L);
        question.setChatId(1L);

        logger.info(zhipuAI.chat(question).getContent());
    }

    @Test
    public void chatTest2() throws Exception {
        SimpleQuestion question = new SimpleQuestion();
        question.setRag(0);
        question.setPromptId(1L);
        question.setContent("请给一段c++");
        question.setUserId(1L);
        question.setChatId(1L);

        logger.info(zhipuAI.chat(question).getContent());
    }

    @Test
    public void chatTest3() {
        SimpleQuestion question = new SimpleQuestion();
        question.setRag(0);
        question.setUserId(1L);
        question.setChatId(1L);

        Throwable thrown = catchThrowable(() -> zhipuAI.chat(question));
        assertThat(thrown).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void chatTest4() throws Exception {
        SimpleQuestion question = new SimpleQuestion();
        question.setRag(1);
        question.setContent("请给一段c++");
        question.setUserId(1L);
        question.setChatId(1L);

        logger.info(zhipuAI.chat(question).getContent());
    }
}
