package com.edu.xmu.rag.service;

import cn.bugstack.chatglm.model.ChatCompletionRequest;
import cn.bugstack.chatglm.model.ChatCompletionSyncResponse;
import cn.bugstack.chatglm.model.Model;
import cn.bugstack.chatglm.model.Role;
import cn.bugstack.chatglm.session.Configuration;
import cn.bugstack.chatglm.session.OpenAiSession;
import cn.bugstack.chatglm.session.OpenAiSessionFactory;
import cn.bugstack.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import com.alibaba.fastjson.JSON;
import com.edu.xmu.rag.controller.vo.ChatVo;
import com.edu.xmu.rag.controller.vo.MessageVo;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.ChatDao;
import com.edu.xmu.rag.dao.MessageDao;
import com.edu.xmu.rag.dao.bo.Chat;
import com.edu.xmu.rag.dao.bo.Message;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Service
@Transactional
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ChatDao chatDao;

    private final MessageDao messageDao;

    private final OpenAiSession openAiSession;

    @Autowired
    public ChatService(ChatDao chatDao, MessageDao messageDao) {
        this.chatDao = chatDao;
        this.messageDao = messageDao;

        Configuration configuration = new Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("70494edc509feaab2964130ed4aee752.dmAMYZDafwaAVzmc");
        configuration.setLevel(HttpLoggingInterceptor.Level.BODY);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        this.openAiSession = factory.openSession();
    }

    /**
     * 根据聊天Id获取聊天内容
     * @param id 聊天id
     * @return MessageVo List
     */
    public ReturnObject findMessageListByChatId(Long id) throws RuntimeException {
        List<Message> res = messageDao.retrieveByChatId(id);
        return new ReturnObject(res.stream().sorted(Comparator.comparing(Message::getGmtCreate)).
                map(bo -> cloneObj(bo, MessageVo.class))
                .collect(Collectors.toList()));
    }

    /**
     * 根据用户Id获取聊天列表
     * @param id 用户id
     * @return ChatVo List
     */
    public ReturnObject findChatListByUserId(Long id) throws RuntimeException {
        List<Chat> res = chatDao.retrieveByUserId(id);
        return new ReturnObject(res.stream()
                .map(bo -> cloneObj(bo, ChatVo.class))
                .collect(Collectors.toList()));
    }

    public ReturnObject createChat(Chat vo) {
        return new ReturnObject(chatDao.insert(vo));
    }

    public ReturnObject updateChat(ChatVo vo) {
        return new ReturnObject(chatDao.save(cloneObj(vo, Chat.class)));
    }

    public ReturnObject delChat(Long id) {
        messageDao.delList(id);
        return new ReturnObject(chatDao.delById(id));
    }

    public ReturnObject chat(MessageVo vo) {
        Message ask = cloneObj(vo, Message.class);
        ask.setRoleString("user");
        logger.debug("ask = {}", ask);
        try {
            Message answer = getAnswer(ask);
            answer.setChatId(ask.getChatId());
            messageDao.insert(ask);
            return new ReturnObject(messageDao.insert(answer));
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.CHAT_WRONG);
        }
    }

    public Message getAnswer(Message ask) throws Exception {
        ChatCompletionRequest request = new ChatCompletionRequest();
        logger.info("问：{}", ask.getContent());
        request.setModel(Model.GLM_3_5_TURBO); // chatGLM_6b_SSE、chatglm_lite、chatglm_lite_32k、chatglm_std、chatglm_pro
//        request.setTools(new ArrayList<ChatCompletionRequest.Tool>() {
//            private static final long serialVersionUID = -7988151926241837899L;
//            {
//                add(ChatCompletionRequest.Tool.builder()
//                        .type(ChatCompletionRequest.Tool.Type.web_search)
//                        .webSearch(ChatCompletionRequest.Tool.WebSearch.builder().enable(true).searchQuery("c++").build())
//                        .build());
//            }
//        });
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;
            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Role.user.getCode())
                        .content(ask.getContent())
                        .build());
            }
        });
        ChatCompletionSyncResponse response = openAiSession.completionsSync(request);
        logger.info("测试结果：{}", JSON.toJSONString(response));
        ChatCompletionSyncResponse.Choice choice = response.getChoices().get(0);
        return new Message(choice.getMessage().getContent(), choice.getMessage().getRole());
    }
}
