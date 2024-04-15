package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.Knowledge;
import com.edu.xmu.rag.mapper.KnowledgePoMapper;
import com.edu.xmu.rag.mapper.po.KnowledgePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.edu.xmu.rag.core.model.Constants.MAX_RETURN;
import static com.edu.xmu.rag.core.util.Common.cloneObj;
import static com.edu.xmu.rag.core.util.Common.putGmtFields;

@Repository
public class KnowledgeDao {
    private final static Logger logger = LoggerFactory.getLogger(KnowledgeDao.class);

    private final KnowledgePoMapper knowledgePoMapper;

    @Autowired
    public KnowledgeDao(KnowledgePoMapper knowledgePoMapper) {
        this.knowledgePoMapper = knowledgePoMapper;
    }

    public Knowledge findUserById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<KnowledgePo> po = knowledgePoMapper.findById(id);
        if (po.isPresent()) {
            return cloneObj(po.get(), Knowledge.class);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "知识", id));
        }
    }

    public Knowledge insert(Knowledge bo) throws RuntimeException {
        KnowledgePo po = cloneObj(bo, KnowledgePo.class);
        putGmtFields(po, "create");
        logger.debug("insertKnowledge: po = {}", po);
        this.knowledgePoMapper.save(po);
        return cloneObj(po, Knowledge.class);
    }

    public Knowledge save(Knowledge bo) {
        KnowledgePo po = cloneObj(bo, KnowledgePo.class);
        putGmtFields(po, "modified");
        logger.debug("saveKnowledge: po = {}", po);
        KnowledgePo save = this.knowledgePoMapper.save(po);
        if (save.getId() == -1) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "知识库", bo.getId()));
        }
        return cloneObj(save, Knowledge.class);
    }

    public ReturnObject delById(Long id) {
        this.knowledgePoMapper.deleteById(id);
        return new ReturnObject(ReturnNo.OK);
    }

    public List<Knowledge> retrieveByKBId(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }

        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<KnowledgePo> ret = knowledgePoMapper.findMessagePoByKbId(id, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            return ret.stream().map(po -> cloneObj(po, Knowledge.class)).toList();
        }
    }
}
