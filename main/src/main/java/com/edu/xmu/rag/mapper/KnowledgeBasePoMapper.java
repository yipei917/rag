package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.KnowledgeBasePo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KnowledgeBasePoMapper extends JpaRepository<KnowledgeBasePo, Long>{
    Page<KnowledgeBasePo> findKnowledgeBasePoByUserId(Long userId, Pageable pageable);
    Optional<KnowledgeBasePo> findKnowledgeBasePoByCode(String code);
}
