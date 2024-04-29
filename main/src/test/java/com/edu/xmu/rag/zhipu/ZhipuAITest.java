package com.edu.xmu.rag.zhipu;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.controller.vo.SimpleQuestion;
import com.edu.xmu.rag.service.ZhipuAI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class ZhipuAITest {
    @Autowired
    private ZhipuAI zhipuAI;

    @Test
    public void simpleChat() throws Exception {
        SimpleQuestion question = new SimpleQuestion();
        question.setContent("请输出一段c++");
        question.setRag(0);
        zhipuAI.chat(question);
    }

}
