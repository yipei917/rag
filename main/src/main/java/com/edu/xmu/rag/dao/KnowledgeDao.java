package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.Knowledge;
import com.edu.xmu.rag.mapper.KnowledgePoMapper;
import com.edu.xmu.rag.mapper.po.KnowledgePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Repository
public class KnowledgeDao {
    private final static Logger logger = LoggerFactory.getLogger(KnowledgeDao.class);

    private final KnowledgePoMapper knowledgePoMapper;

    private final KnowledgeBaseDao knowledgeBaseDao;

    @Autowired
    public KnowledgeDao(KnowledgePoMapper knowledgePoMapper, KnowledgeBaseDao knowledgeBaseDao) {
        this.knowledgePoMapper = knowledgePoMapper;
        this.knowledgeBaseDao = knowledgeBaseDao;
    }

    private void setBo(Knowledge bo) {
        bo.setKnowledgeBaseDao(this.knowledgeBaseDao);
    }

    private Knowledge getBo(KnowledgePo po) {
        Knowledge ret = cloneObj(po, Knowledge.class);
        this.setBo(ret);
        return ret;
    }

    public Knowledge findUserById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<KnowledgePo> po = knowledgePoMapper.findById(id);
        if (po.isPresent()) {
            return cloneObj(po, Knowledge.class);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "知识", id));
        }
    }
}
