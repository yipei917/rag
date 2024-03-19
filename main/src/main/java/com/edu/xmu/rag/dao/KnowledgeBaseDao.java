package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import com.edu.xmu.rag.dao.bo.User;
import com.edu.xmu.rag.mapper.KnowledgeBasePoMapper;
import com.edu.xmu.rag.mapper.po.KnowledgeBasePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Repository
public class KnowledgeBaseDao {
    private final static Logger logger = LoggerFactory.getLogger(KnowledgeBaseDao.class);

    private final KnowledgeBasePoMapper knowledgeBasePoMapper;

    private final UserDao userDao;

    @Autowired
    KnowledgeBaseDao(KnowledgeBasePoMapper knowledgeBasePoMapper, UserDao userDao) {
        this.knowledgeBasePoMapper = knowledgeBasePoMapper;
        this.userDao = userDao;
    }

    private void setBo(KnowledgeBase bo) {
        bo.setUserDao(this.userDao);
    }

    private KnowledgeBase getBo(KnowledgeBasePo po) {
        KnowledgeBase ret = cloneObj(po, KnowledgeBase.class);
        this.setBo(ret);
        return ret;
    }

    public KnowledgeBase findUserById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<KnowledgeBasePo> po = knowledgeBasePoMapper.findById(id);
        if (po.isPresent()) {
            return cloneObj(po.get(), KnowledgeBase.class);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "知识库", id));
        }
    }
}
