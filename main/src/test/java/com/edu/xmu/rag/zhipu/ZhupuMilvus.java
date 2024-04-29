package com.edu.xmu.rag.zhipu;

import com.edu.xmu.rag.MainApplication;
import com.edu.xmu.rag.controller.vo.SimpleQuestion;
import com.edu.xmu.rag.dao.bo.ChatData;
import com.edu.xmu.rag.llm.result.ZhipuEmbeddingResult;
import com.edu.xmu.rag.service.MilvusService;
import com.edu.xmu.rag.service.ZhipuAI;
import com.edu.xmu.rag.service.dto.ChunkResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class ZhupuMilvus {
    @Autowired
    MilvusService milvusService;

    @Autowired
    ZhipuAI zhipuAI;

    @Test
    public void save() {
        String content = "厦门市，简称厦或鹭，福建省辖地级市、副省级市、计划单列市，位于中国华东地区、福建省东南部沿海，与漳州市、泉州市相连，地形以滨海平原、台地和丘陵为主，属南亚热带海洋性季风气候，温和多雨，截至2023年10月，厦门市下辖6个区，总面积1700.61平方千米，截至2023年末，厦门市常住人口532.70万人，常住人口城镇化率90.81%。" +
                "远古时期，厦门岛为白鹭栖息之地，故又称鹭岛，1935年，设立厦门市，1980年，经国务院批准设立厦门经济特区，1988年，经国务院批准厦门市为计划单列市，1994年，厦门市升为副省级市。厦门市是国务院批复确定的中国经济特区和东南沿海重要的中心城市、港口及风景旅游城市，"+
                "2023年，厦门市实现地区生产值8066.49亿元，其中，第一产业增加值27.73亿元，第二产业增加值2867.94亿元，第三产业增加值5170.81亿元";
        milvusService.save(content, "厦门");
        String content2 = "Hibernate是一个优秀的持久化框架，负责简化将对象数据保存到数据库中或从数据库中读取数据并封装到对象的工作。通过简单的配置和编码即可替代JDBC繁锁的程序代码。Hibernate最重大的意义在于可以在应用EJB的J2EE架构中取代CMP，完成数据持久化的重任。";
        milvusService.save(content2, "厦门");
    }

    @Test
    public void search() {
        ChunkResult chunk = new ChunkResult();
        chunk.setChunkId(0);
        chunk.setContent("厦门的简称是什么");
        chunk.setDocId("厦门");
        ZhipuEmbeddingResult embeddingResult = milvusService.embedding(chunk);
        List<String> list = milvusService.search(Collections.singletonList(embeddingResult.getEmbedding()));
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void chat() {
        SimpleQuestion question = new SimpleQuestion();
        question.setChatId(1L);
        question.setContent("厦门的简称是什么");
        question.setRag(1);
        try {
            zhipuAI.chat(question);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
