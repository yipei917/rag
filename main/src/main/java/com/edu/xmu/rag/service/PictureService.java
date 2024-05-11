package com.edu.xmu.rag.service;

import cn.bugstack.chatglm.model.ImageCompletionRequest;
import cn.bugstack.chatglm.model.ImageCompletionResponse;
import cn.bugstack.chatglm.model.Model;
import cn.bugstack.chatglm.session.Configuration;
import cn.bugstack.chatglm.session.OpenAiSession;
import cn.bugstack.chatglm.session.OpenAiSessionFactory;
import cn.bugstack.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.ChatDao;
import com.edu.xmu.rag.dao.MessageDao;
import com.edu.xmu.rag.dao.bo.Message;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {
    private final OpenAiSession openAiSession;

    private final MessageDao messageDao;

    @Autowired
    public PictureService(MessageDao messageDao) {
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("70494edc509feaab2964130ed4aee752.dmAMYZDafwaAVzmc");
        configuration.setLevel(HttpLoggingInterceptor.Level.BODY);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        this.openAiSession = factory.openSession();

        this.messageDao = messageDao;
    }

    public ReturnObject txt2pic(String content, Long uid) {
        Long chatId = uid + 10000;
        ImageCompletionRequest request = new ImageCompletionRequest();
        request.setModel(Model.COGVIEW_3);
        request.setPrompt(content);
        try {
            ImageCompletionResponse response = openAiSession.genImages(request);
            Message ask = Message.builder().content(content).role(2).chatId(chatId).build();
            Message answer = Message.builder().content(response.getData().get(0).getUrl()).role(1).chatId(chatId).build();
            messageDao.insert(ask);
            messageDao.insert(answer);
            return new ReturnObject(response);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.CHAT_WRONG);
        }
    }

    public ReturnObject getPic(Long userId) {
        return new ReturnObject(messageDao.retrieveByChatId(userId + 10000));
    }
}
