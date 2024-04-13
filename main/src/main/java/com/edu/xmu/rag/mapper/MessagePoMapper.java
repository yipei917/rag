package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.MessagePo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessagePoMapper extends JpaRepository<MessagePo, Long> {
    Page<MessagePo> findMessagePoByChatId(Long chatId, Pageable pageable);
}
