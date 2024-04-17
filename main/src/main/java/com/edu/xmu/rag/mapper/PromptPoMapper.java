package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.KnowledgePo;
import com.edu.xmu.rag.mapper.po.PromptPo;
import com.edu.xmu.rag.mapper.po.UserPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromptPoMapper extends JpaRepository<PromptPo, Long> {

    Page<PromptPo> findPromptPoByUserIdAndModelId(Long userId, Long modelId, Pageable pageable);

    Page<PromptPo> findPromptPoByUserId(Long userId, Pageable pageable);

    Page<PromptPo> findPromptPoByModelId(Long modelId, Pageable pageable);
}