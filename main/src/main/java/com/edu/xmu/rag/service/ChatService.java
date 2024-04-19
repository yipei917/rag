package com.edu.xmu.rag.service;

import com.edu.xmu.rag.controller.vo.ChatVo;
import com.edu.xmu.rag.controller.vo.MessageVo;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.ChatDao;
import com.edu.xmu.rag.dao.MessageDao;
import com.edu.xmu.rag.dao.bo.Chat;
import com.edu.xmu.rag.dao.bo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Service
@Transactional
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ChatDao chatDao;

    private final MessageDao messageDao;

    @Autowired
    public ChatService(ChatDao chatDao, MessageDao messageDao) {
        this.chatDao = chatDao;
        this.messageDao = messageDao;
    }

    /**
     * 根据聊天Id获取聊天内容
     * @param id 聊天id
     * @return MessageVo List
     */
    public ReturnObject findMessageListByChatId(Long id) throws RuntimeException {
        List<Message> res = messageDao.retrieveByChatId(id);
        return new ReturnObject(res.stream().sorted(Comparator.comparing(Message::getGmtCreate)).map(bo -> cloneObj(bo, MessageVo.class)).toList());
    }

    /**
     * 根据用户Id获取聊天列表
     * @param id 用户id
     * @return ChatVo List
     */
    public ReturnObject findChatListByUserId(Long id) throws RuntimeException {
        List<Chat> res = chatDao.retrieveByUserId(id);
        return new ReturnObject(res.stream().map(bo -> cloneObj(bo, ChatVo.class)).toList());
    }

    public ReturnObject createChat(Chat vo) {
        return new ReturnObject(chatDao.insert(vo));
    }

    public ReturnObject updateChat(ChatVo vo) {
        return new ReturnObject(chatDao.save(cloneObj(vo, Chat.class)));
    }

    public ReturnObject delChat(Long id) {
        return new ReturnObject(chatDao.delById(id));
    }
}
