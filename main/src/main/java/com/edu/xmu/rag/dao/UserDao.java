package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.User;
import com.edu.xmu.rag.mapper.UserPoMapper;
import com.edu.xmu.rag.mapper.po.UserPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Repository
public class UserDao {
    private final static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final UserPoMapper userPoMapper;

    @Autowired
    public UserDao(UserPoMapper userPoMapper) {
        this.userPoMapper = userPoMapper;
    }

    public User findUserById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<UserPo> po = userPoMapper.findById(id);
        if (po.isPresent()) {
            return cloneObj(po, User.class);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "用户", id));
        }
    }
}
