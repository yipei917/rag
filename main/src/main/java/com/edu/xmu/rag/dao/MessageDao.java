package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.Message;
import com.edu.xmu.rag.mapper.MessagePoMapper;
import com.edu.xmu.rag.mapper.po.MessagePo;
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
import java.util.stream.Collectors;

import static com.edu.xmu.rag.core.model.Constants.MAX_RETURN;
import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Repository
public class MessageDao {
    private final static Logger logger = LoggerFactory.getLogger(MessageDao.class);

    private final MessagePoMapper messagePoMapper;

    @Autowired
    public MessageDao(MessagePoMapper messagePoMapper) {
        this.messagePoMapper = messagePoMapper;
    }

    public Message findMessageById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<MessagePo> po = messagePoMapper.findById(id);
        if (po.isPresent()) {
            return cloneObj(po.get(), Message.class);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "聊天详情", id));
        }
    }

    public List<Message> retrieveByChatId(Long chatId) throws RuntimeException {
        if (null == chatId) {
            return null;
        }

        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<MessagePo> ret = messagePoMapper.findMessagePoByChatId(chatId, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            return ret.stream()
                    .map(po -> cloneObj(po, Message.class))
                    .collect(Collectors.toList());
        }
    }
}
