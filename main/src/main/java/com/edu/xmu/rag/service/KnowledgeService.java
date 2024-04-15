package com.edu.xmu.rag.service;

import com.edu.xmu.rag.controller.vo.SimpleKnowledgeBase;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.KnowledgeBaseDao;
import com.edu.xmu.rag.dao.KnowledgeDao;
import com.edu.xmu.rag.dao.bo.Knowledge;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Service
@Transactional
public class KnowledgeService {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);

    private final KnowledgeDao knowledgeDao;

    private final KnowledgeBaseDao knowledgeBaseDao;

    @Autowired
    public KnowledgeService(KnowledgeDao knowledgeDao, KnowledgeBaseDao knowledgeBaseDao) {
        this.knowledgeDao = knowledgeDao;
        this.knowledgeBaseDao = knowledgeBaseDao;
    }

    /*
    新增知识库
     */
    public ReturnObject createKnowledgeBase(SimpleKnowledgeBase vo) {
        KnowledgeBase ret = cloneObj(vo, KnowledgeBase.class);
        ret.setStatus(1);
        return new ReturnObject(knowledgeBaseDao.insert(ret));
    }

    public ReturnObject updateKnowledgeBase(KnowledgeBase vo) {
        return new ReturnObject(knowledgeBaseDao.save(vo));
    }

    public ReturnObject enableKnowledgeBase(Long id) {
        KnowledgeBase bo = knowledgeBaseDao.findUserById(id);
        bo.setStatus(1);
        knowledgeBaseDao.save(bo);
        return new ReturnObject(ReturnNo.OK);
    }

    public ReturnObject disableKnowledgeBase(Long id) {
        KnowledgeBase bo = knowledgeBaseDao.findUserById(id);
        bo.setStatus(0);
        knowledgeBaseDao.save(bo);
        return new ReturnObject(ReturnNo.OK);
    }

    public ReturnObject delKnowledgeBase(Long id) {
        return knowledgeBaseDao.delById(id);
    }

    public ReturnObject findKnowledgeBaseByUserId(Long id) throws RuntimeException {
        return new ReturnObject(knowledgeBaseDao.retrieveByUserId(id));
    }


}
