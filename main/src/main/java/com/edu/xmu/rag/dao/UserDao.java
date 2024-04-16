package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.Message;
import com.edu.xmu.rag.dao.bo.User;
import com.edu.xmu.rag.mapper.UserPoMapper;
import com.edu.xmu.rag.mapper.po.UserPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
            return cloneObj(po.get(), User.class);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "用户", id));
        }
    }

    public Optional<User> findByName(String name) throws RuntimeException {
        AtomicReference<User> user = new AtomicReference<>();
        if (null != name) {
            logger.debug("findObjByUserName: userName = {}", name);
            Optional<UserPo> ret = this.userPoMapper.findByName(name);
            ret.ifPresent(po -> user.set(cloneObj(po, User.class)));
            if (null == user.get()) {
                throw new BusinessException(ReturnNo.USER_INVALID_ACCOUNT,ReturnNo.USER_INVALID_ACCOUNT.getMessage());
            }
        }
        return Optional.of(user.get());
    }

    public User insert(User user) throws RuntimeException {
        UserPo po = cloneObj(user, UserPo.class);
        logger.debug("insertUser: po = {}", po);
        this.userPoMapper.save(po);
        return cloneObj(po, User.class);
    }

    public User save(User user) {
        UserPo po = cloneObj(user, UserPo.class);
        logger.debug("saveUser: po = {}", po);
        UserPo save = this.userPoMapper.save(po);
        if (save.getId() == -1) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "用户", user.getId()));
        }
        return cloneObj(save, User.class);
    }

    public ReturnObject delById(User user) {
        userPoMapper.deleteById(user.getId());
        return new ReturnObject(ReturnNo.OK);
    }

    public List<User> findAllUsers() {
        List<UserPo> userPos = userPoMapper.findAll();
        return userPos.stream()
                .map(userPo -> cloneObj(userPo, User.class))
                .collect(Collectors.toList());
    }
}
