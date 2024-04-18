package com.edu.xmu.rag.service;

import com.edu.xmu.rag.controller.vo.UserVo;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.UserDao;
import com.edu.xmu.rag.dao.bo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;

    @Autowired
    UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 注册
     */
    public ReturnObject register(UserVo user) {
        try {
            userDao.findByName(user.getName());
        } catch (BusinessException e) {
            User ret = cloneObj(user, User.class);
            ret.setType(1);
            userDao.insert(ret);
            return new ReturnObject(ReturnNo.CREATED);
        }
        return new ReturnObject(ReturnNo.USER_NAME_EXIST);
    }

    /**
     * 登录
     */
    public ReturnObject login(UserVo user) {
        User ret = userDao.findByName(user.getName()).orElse(null);
        if (ret != null && ret.getPassword().equals(user.getPassword())) {
            return new ReturnObject(cloneObj(ret, UserVo.class));
        } else {
            throw new BusinessException(ReturnNo.USER_INVALID_ACCOUNT, ReturnNo.USER_INVALID_ACCOUNT.getMessage());
        }
    }

    public ReturnObject getUserById(Long id) {
        User user = userDao.findUserById(id);
        if (user != null) {
            return new ReturnObject(ReturnNo.OK, user);
        } else {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOT_EXIST);
        }
    }

    public ReturnObject createUser(User user) {
        try {
            userDao.findByName(user.getName());
        } catch (BusinessException e) {
            return new ReturnObject(ReturnNo.CREATED, userDao.insert(user));
        }
        return new ReturnObject(ReturnNo.USER_NAME_EXIST);
    }

    public ReturnObject updateUser(User user) {
        User ret = userDao.findUserById(user.getId());
        if(ret != null)
        {
            ret.setName(user.getName());
            ret.setPassword(user.getPassword());
            ret.setToken(user.getToken());
            userDao.insert(ret);
            return new ReturnObject(ReturnNo.OK, cloneObj(user, User.class));
        }else {
            throw new BusinessException(ReturnNo.USER_INVALID_ACCOUNT);
        }

    }

    public ReturnObject deleteUserById(User user) {
        User ret = userDao.findUserById(user.getId());
        if (ret != null) {
            userDao.delById(user);
            return new ReturnObject(ReturnNo.OK);
        } else{
            throw new BusinessException(ReturnNo.USER_INVALID_ACCOUNT);
        }
    }

    public ReturnObject getAllUsers() {
        List<User> res = userDao.findAllUsers();
        return new ReturnObject(res);
    }
}
