package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.KnowledgePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgePoMapper extends JpaRepository<KnowledgePo, Long> {
}
