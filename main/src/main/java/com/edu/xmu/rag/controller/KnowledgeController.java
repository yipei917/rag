package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.controller.vo.KnowledgeVo;
import com.edu.xmu.rag.controller.vo.SimpleKnowledge;
import com.edu.xmu.rag.controller.vo.SimpleKnowledgeBase;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.controller.vo.SimpleSearch;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.service.IChatService;
import com.edu.xmu.rag.service.KnowledgeService;
import com.edu.xmu.rag.service.MilvusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class KnowledgeController {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeController.class);

    private final KnowledgeService knowledgeService;

    private final MilvusService milvusService;
    @Autowired
    public KnowledgeController(KnowledgeService knowledgeService, MilvusService milvusService) {
        this.knowledgeService = knowledgeService;
        this.milvusService = milvusService;
    }

    @PostMapping("/knowledgebase")
    public ReturnObject createKnowledgeBase(@Validated @RequestBody SimpleKnowledgeBase vo) {
        if (knowledgeService.isCodeExist(vo.getCode())) return new ReturnObject(ReturnNo.CODE_EXIST);
        this.milvusService.createKnowledgeBase(vo.getCode());
        return knowledgeService.createKnowledgeBase(vo);
    }

    @PutMapping("/knowledgebase")
    public ReturnObject updateKnowledgeBase(@Validated @RequestBody SimpleKnowledgeBase vo) {
        return knowledgeService.updateKnowledgeBase(vo);
    }

    @DeleteMapping("/knowledgebase/{id}")
    public ReturnObject delKnowledgeBase(@PathVariable Long id) {
        this.milvusService.dropCollection(knowledgeService.findKnowledgeBaseCode(id));
        return knowledgeService.delKnowledgeBase(id);
    }

    @PutMapping("/knowledgebase/{id}/{type}")
    public ReturnObject changeKnowledgeBaseStatus(@PathVariable Long id, @PathVariable Integer type) {
        if (type == 0) {
            return knowledgeService.disableKnowledgeBase(id);
        } else {
            return knowledgeService.enableKnowledgeBase(id);
        }
    }

    @GetMapping("/knowledgebase/{uid}/{code}")
    public ReturnObject findKnowledgeBase(@PathVariable Long uid, @PathVariable String code) {
        return knowledgeService.findKnowledgeBase(uid, code);
    }

    //新建知识并写入向量数据库
    @PostMapping("/knowledge")
    public ReturnObject createKnowledge(@Validated @RequestBody SimpleKnowledge vo) {
        milvusService.save(vo.getContent(), knowledgeService.findKnowledgeBaseCode(vo.getKbId()));
        return knowledgeService.createKnowledge(vo);
    }

    @PutMapping("/knowledge")
    public ReturnObject updateKnowledge(@Validated @RequestBody SimpleKnowledge vo) {
        return knowledgeService.updateKnowledge(vo);
    }

    @DeleteMapping("/knowledge/{id}")
    public ReturnObject delKnowledge(@PathVariable Long id) {
        return knowledgeService.delKnowledge(id);
    }

    @PutMapping("/knowledge/{id}/{type}")
    public ReturnObject changeKnowledgeStatus(@PathVariable Long id, @PathVariable Integer type) {
        if (type == 0) {
            return knowledgeService.disableKnowledge(id);
        } else {
            return knowledgeService.enableKnowledge(id);
        }
    }

    @GetMapping("/knowledge")
    public ReturnObject findKnowledge(@Validated @RequestBody SimpleSearch search) {
        return knowledgeService.findKnowledge(search.getId(), search.getCode(), search.getKeyword());
    }
}
