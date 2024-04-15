package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
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
import static com.edu.xmu.rag.core.util.Common.putGmtFields;

@Repository
public class KnowledgeBaseDao {
    private final static Logger logger = LoggerFactory.getLogger(KnowledgeBaseDao.class);

    private final KnowledgeBasePoMapper knowledgeBasePoMapper;

    private final KnowledgeDao knowledgeDao;

    @Autowired
    KnowledgeBaseDao(KnowledgeBasePoMapper knowledgeBasePoMapper, KnowledgeDao knowledgeDao) {
        this.knowledgeBasePoMapper = knowledgeBasePoMapper;
        this.knowledgeDao = knowledgeDao;
    }

    private void setBo(KnowledgeBase bo) {
        bo.setKnowledgeDao(this.knowledgeDao);
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
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "知识库", id));
        }
    }

    public KnowledgeBase insert(KnowledgeBase bo) throws RuntimeException {
        KnowledgeBasePo po = cloneObj(bo, KnowledgeBasePo.class);
        putGmtFields(po, "create");
        logger.debug("insertKnowledgeBase: po = {}", po);
        this.knowledgeBasePoMapper.save(po);
        return cloneObj(po, KnowledgeBase.class);
    }

    public KnowledgeBase save(KnowledgeBase bo) {
        KnowledgeBasePo po = cloneObj(bo, KnowledgeBasePo.class);
        putGmtFields(po, "modified");
        logger.debug("saveKnowledgeBase: po = {}", po);
        KnowledgeBasePo save = this.knowledgeBasePoMapper.save(po);
        if (save.getId() == -1) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "知识库", bo.getId()));
        }
        return cloneObj(save, KnowledgeBase.class);
    }

    public ReturnObject delById(KnowledgeBase bo) {
        this.knowledgeBasePoMapper.deleteById(bo.getId());
        return new ReturnObject(ReturnNo.OK);
    }
}
