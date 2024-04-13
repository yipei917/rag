package com.edu.xmu.rag.mapper;

import com.edu.xmu.rag.mapper.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPoMapper extends JpaRepository<UserPo, Long> {
    Optional<UserPo> findByName(String name);
}
