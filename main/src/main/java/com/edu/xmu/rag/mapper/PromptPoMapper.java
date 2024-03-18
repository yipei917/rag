package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.PromptPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptPoMapper extends JpaRepository<PromptPo, Long> {
}