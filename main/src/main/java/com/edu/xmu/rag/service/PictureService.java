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
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {
    private final OpenAiSession openAiSession;

    @Autowired
    public PictureService() {
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("70494edc509feaab2964130ed4aee752.dmAMYZDafwaAVzmc");
        configuration.setLevel(HttpLoggingInterceptor.Level.BODY);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        this.openAiSession = factory.openSession();
    }

    public ReturnObject txt2pic(String content) {
        ImageCompletionRequest request = new ImageCompletionRequest();
        request.setModel(Model.COGVIEW_3);
        request.setPrompt(content);
        try {
            ImageCompletionResponse response = openAiSession.genImages(request);
            return new ReturnObject(response);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.CHAT_WRONG);
        }
    }
}
