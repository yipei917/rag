package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.Chat;
import com.edu.xmu.rag.mapper.ChatPoMapper;
import com.edu.xmu.rag.mapper.po.ChatPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.edu.xmu.rag.core.model.Constants.MAX_RETURN;
import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Repository
public class ChatDao {
    private final static Logger logger = LoggerFactory.getLogger(ChatDao.class);

    private final ChatPoMapper chatPoMapper;

    private final UserDao userDao;

    private final MessageDao messageDao;

    @Autowired
    public ChatDao(ChatPoMapper chatPoMapper, UserDao userDao, MessageDao messageDao) {
        this.chatPoMapper = chatPoMapper;
        this.userDao = userDao;
        this.messageDao = messageDao;
    }

    private void setBo(Chat bo) {
        bo.setUserDao(this.userDao);
        bo.setMessageDao(this.messageDao);
    }

    private Chat getBo(ChatPo po) {
        Chat ret = cloneObj(po, Chat.class);
        this.setBo(ret);
        return ret;
    }

    public Chat findChatById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<ChatPo> po = chatPoMapper.findById(id);
        if (po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "会话", id));
        }
    }

    public List<Chat> retrieveByUserId(Long userId) throws RuntimeException {
        if (null == userId) {
            return null;
        }

        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<ChatPo> ret = chatPoMapper.findChatPoByUserId(userId, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            return ret.stream().map(po -> cloneObj(po, Chat.class)).toList();
        }
    }
}
