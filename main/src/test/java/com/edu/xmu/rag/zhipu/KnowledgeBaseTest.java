package com.edu.xmu.rag.zhipu;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.controller.KnowledgeController;
import com.edu.xmu.rag.controller.vo.SimpleKnowledge;
import com.edu.xmu.rag.controller.vo.SimpleKnowledgeBase;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import com.edu.xmu.rag.service.KnowledgeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class KnowledgeBaseTest {
    @Autowired
    KnowledgeController knowledgeController;

    @Test
    public void createKnowledgeBase() {
        SimpleKnowledgeBase vo = new SimpleKnowledgeBase();
        vo.setCode("test");
        vo.setUserId(1L);
        vo.setTitle("XiaMen");
        ReturnObject res = knowledgeController.createKnowledgeBase(vo);
    }

    @Test
    public void createKnowledge() {
        SimpleKnowledge vo = new SimpleKnowledge();
        vo.setContent("厦门市，简称厦或鹭，福建省辖地级市、副省级市、计划单列市，位于中国华东地区、福建省东南部沿海，与漳州市、泉州市相连，地形以滨海平原、台地和丘陵为主，属南亚热带海洋性季风气候，温和多雨，截至2023年10月，厦门市下辖6个区，总面积1700.61平方千米，截至2023年末，厦门市常住人口532.70万人，常住人口城镇化率90.81%。" +
                "远古时期，厦门岛为白鹭栖息之地，故又称鹭岛，1935年，设立厦门市，1980年，经国务院批准设立厦门经济特区，1988年，经国务院批准厦门市为计划单列市，1994年，厦门市升为副省级市。厦门市是国务院批复确定的中国经济特区和东南沿海重要的中心城市、港口及风景旅游城市，"+
                "2023年，厦门市实现地区生产值8066.49亿元，其中，第一产业增加值27.73亿元，第二产业增加值2867.94亿元，第三产业增加值5170.81亿元");
        vo.setCode("test-1");
        vo.setKbId(11L);
        knowledgeController.createKnowledge(vo);
    }

    @Test
    public void delKnowledge() {
        knowledgeController.delKnowledgeBase(11L);
    }
}
