package com.edu.xmu.rag.service;

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
}
