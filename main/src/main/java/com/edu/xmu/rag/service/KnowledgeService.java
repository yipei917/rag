package com.edu.xmu.rag.service;

import com.edu.xmu.rag.controller.vo.KnowledgeVo;
import com.edu.xmu.rag.controller.vo.SimpleKnowledge;
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
import java.util.stream.Collectors;

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
        return new ReturnObject(ReturnNo.CREATED, knowledgeBaseDao.insert(ret));
    }

    /*
    更新知识库
     */
    public ReturnObject updateKnowledgeBase(SimpleKnowledgeBase vo) {
        KnowledgeBase bo = cloneObj(vo, KnowledgeBase.class);
        return new ReturnObject(knowledgeBaseDao.save(bo));
    }

    /*
    启用知识库
     */
    public ReturnObject enableKnowledgeBase(Long id) {
        KnowledgeBase bo = knowledgeBaseDao.findUserById(id);
        bo.setStatus(1);
        knowledgeBaseDao.save(bo);
        return new ReturnObject(ReturnNo.OK);
    }

    /*
    禁用知识库
     */
    public ReturnObject disableKnowledgeBase(Long id) {
        KnowledgeBase bo = knowledgeBaseDao.findUserById(id);
        bo.setStatus(0);
        knowledgeBaseDao.save(bo);
        return new ReturnObject(ReturnNo.OK);
    }

    /*
    删除知识库
     */
    public ReturnObject delKnowledgeBase(Long id) {
        return knowledgeBaseDao.delById(id);
    }

    /*
    根据用户id和知识库code查询知识库
     */
    public ReturnObject findKnowledgeBase(Long id, String code) throws RuntimeException {
        List<KnowledgeBase> list;
        if (0 == id) {
            list = knowledgeBaseDao.retrieveAll();
        } else {
            list = knowledgeBaseDao.retrieveByUserId(id);
        }
        if (code.equals("*")) {
            return new ReturnObject(list);
        } else {
            return new ReturnObject(list.stream().filter(po -> po.getCode().equals(code)).toList());
        }
    }

    /*
    新建知识
     */
    public ReturnObject createKnowledge(SimpleKnowledge vo) {
        Knowledge ret = cloneObj(vo, Knowledge.class);
        ret.setStatus(1);
        return new ReturnObject(ReturnNo.CREATED, cloneObj(knowledgeDao.insert(ret), KnowledgeVo.class));
    }

    /*
    更新知识
     */
    public ReturnObject updateKnowledge(SimpleKnowledge vo) {
        Knowledge bo = cloneObj(vo, Knowledge.class);
        return new ReturnObject(cloneObj(knowledgeDao.insert(bo), KnowledgeVo.class));
    }

    /*
    启用知识
     */
    public ReturnObject enableKnowledge(Long id) {
        Knowledge bo = knowledgeDao.findUserById(id);
        bo.setStatus(1);
        knowledgeDao.save(bo);
        return new ReturnObject(ReturnNo.OK);
    }

    /*
    禁用知识
     */
    public ReturnObject disableKnowledge(Long id) {
        Knowledge bo = knowledgeDao.findUserById(id);
        bo.setStatus(0);
        knowledgeDao.save(bo);
        return new ReturnObject(ReturnNo.OK);
    }

    /*
    删除知识
     */
    public ReturnObject delKnowledge(Long id) {
        return knowledgeDao.delById(id);
    }

    /*
    根据知识库id和知识code查询知识
     */
    public ReturnObject findKnowledge(Long id, String code) throws RuntimeException {
        List<KnowledgeVo> list = knowledgeDao.retrieveByKBId(id).stream().map(bo -> cloneObj(bo, KnowledgeVo.class)).toList();
        if (code.equals("*")) {
            return new ReturnObject(list.stream());
        } else {
            return new ReturnObject(list.stream().filter(bo -> bo.getCode().equals(code)).toList());
        }
    }
}
