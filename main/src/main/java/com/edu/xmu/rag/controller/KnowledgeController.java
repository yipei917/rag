package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.controller.vo.KnowledgeVo;
import com.edu.xmu.rag.controller.vo.SimpleKnowledge;
import com.edu.xmu.rag.controller.vo.SimpleKnowledgeBase;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.service.IChatService;
import com.edu.xmu.rag.service.KnowledgeService;
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

    @Autowired
    private IChatService chatServiceImpl;

    @Autowired
    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @PostMapping("/knowledgebase")
    public ReturnObject createKnowledgeBase(@Validated @RequestBody SimpleKnowledgeBase vo) {
        return knowledgeService.createKnowledgeBase(vo);
    }

    @PutMapping("/knowledgebase")
    public ReturnObject updateKnowledgeBase(@Validated @RequestBody SimpleKnowledgeBase vo) {
        return knowledgeService.updateKnowledgeBase(vo);
    }

    @DeleteMapping("/knowledgebase/{id}")
    public ReturnObject delKnowledgeBase(@PathVariable Long id) {
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

    @PostMapping("/knowledge")
    public ReturnObject createKnowledge(@Validated @RequestBody SimpleKnowledge vo) {
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

    @GetMapping("/knowledge/{kbid}/{code}")
    public ReturnObject findKnowledge(@PathVariable Long kbid, @PathVariable String code) {
        return knowledgeService.findKnowledge(kbid, code);
    }

    //将知识写入向量数据库
    @PostMapping("/upload/{kbid}/{code}")
    public ReturnObject embedKnowledge(@PathVariable Long kbid, @PathVariable String code) {
        String knowledge = "厦门市，简称厦或鹭，福建省辖地级市、副省级市、计划单列市，位于中国华东地区、福建省东南部沿海，与漳州市、泉州市相连，地形以滨海平原、台地和丘陵为主，属南亚热带海洋性季风气候，温和多雨，截至2023年10月，厦门市下辖6个区，总面积1700.61平方千米，截至2023年末，厦门市常住人口532.70万人，常住人口城镇化率90.81%。" +
                "远古时期，厦门岛为白鹭栖息之地，故又称鹭岛，1935年，设立厦门市，1980年，经国务院批准设立厦门经济特区，1988年，经国务院批准厦门市为计划单列市，1994年，厦门市升为副省级市。厦门市是国务院批复确定的中国经济特区和东南沿海重要的中心城市、港口及风景旅游城市，"+
                "2023年，厦门市实现地区生产值8066.49亿元，其中，第一产业增加值27.73亿元，第二产业增加值2867.94亿元，第三产业增加值5170.81亿元";
        //String knowledge = knowledgeService.findKnowledge(kbid, code);
        //
        return new ReturnObject(chatServiceImpl.save(knowledge));
    }
}
