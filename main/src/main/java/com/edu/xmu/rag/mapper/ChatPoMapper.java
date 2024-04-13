package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.ChatPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatPoMapper extends JpaRepository<ChatPo, Long> {
    Page<ChatPo> findChatPoByUserId(Long userID, Pageable pageable);
}
