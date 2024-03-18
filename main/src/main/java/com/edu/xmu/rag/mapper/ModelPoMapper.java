package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.ModelPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelPoMapper extends JpaRepository<ModelPo, Long> {
}
