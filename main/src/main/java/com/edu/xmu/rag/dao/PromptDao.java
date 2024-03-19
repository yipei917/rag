package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.Prompt;
import com.edu.xmu.rag.mapper.PromptPoMapper;
import com.edu.xmu.rag.mapper.po.PromptPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Repository
public class PromptDao {
    private final static Logger logger = LoggerFactory.getLogger(PromptDao.class);

    private final PromptPoMapper promptPoMapper;

    private final UserDao userDao;

    private final ModelDao modelDao;

    @Autowired
    public PromptDao(PromptPoMapper promptPoMapper, UserDao userDao, ModelDao modelDao) {
        this.promptPoMapper = promptPoMapper;
        this.userDao = userDao;
        this.modelDao = modelDao;
    }

    private void setBo(Prompt bo) {
        bo.setUserDao(this.userDao);
        bo.setModelDao(this.modelDao);
    }

    private Prompt getBo(PromptPo po) {
        Prompt ret = cloneObj(po, Prompt.class);
        this.setBo(ret);
        return ret;
    }

    public Prompt findPromptById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<PromptPo> po = promptPoMapper.findById(id);
        if (po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "提示词", id));
        }
    }
}
