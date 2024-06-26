package com.edu.xmu.rag.service;

import cn.bugstack.chatglm.model.*;
import cn.bugstack.chatglm.session.Configuration;
import cn.bugstack.chatglm.session.OpenAiSession;
import cn.bugstack.chatglm.session.OpenAiSessionFactory;
import cn.bugstack.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import com.edu.xmu.rag.controller.vo.SimpleQuestion;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.Message;
import com.edu.xmu.rag.llm.LLMUtils;
import com.edu.xmu.rag.llm.result.ZhipuEmbeddingResult;
import com.edu.xmu.rag.service.dto.ChunkResult;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ZhipuAI {
    private static final Logger logger = LoggerFactory.getLogger(ZhipuAI.class);

    private final OpenAiSession openAiSession;

    private final MilvusService milvusService;

    private final ManagementService managementService;

    private final KnowledgeService knowledgeService;

    @Autowired
    public ZhipuAI(MilvusService milvusService, ManagementService managementService, KnowledgeService knowledgeService) {
        this.milvusService = milvusService;
        this.managementService = managementService;
        this.knowledgeService = knowledgeService;

        Configuration configuration = new Configuration();
        configuration.setApiHost("https://open.bigmodel.cn/");
        configuration.setApiSecretKey("70494edc509feaab2964130ed4aee752.dmAMYZDafwaAVzmc");
        configuration.setLevel(HttpLoggingInterceptor.Level.BODY);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        this.openAiSession = factory.openSession();
    }

    public Message chat(SimpleQuestion question) throws Exception {
        String _sysPrompt = managementService.getSystemPrompt(question.getPromptId());
        String sysPrompt = (null == _sysPrompt) ? "请根据要求回答" : _sysPrompt;
        String userPrompt = (0 == question.getRag()) ? question.getContent() : this.rag(question);
        return this.getAnswer(userPrompt, sysPrompt);
    }

    private String rag(SimpleQuestion question) {
        //  将问题向量化
        ChunkResult chunk = new ChunkResult("question", 0, question.getContent());
        ZhipuEmbeddingResult embeddingResult = milvusService.embedding(chunk);
        //  根据用户id获取用户全部的知识库编码
        List<String> codes = knowledgeService.findKnowledgeBaseCodeByUserId(question.getUserId());
        //  builder用于接收知识
        StringBuilder builder = new StringBuilder();
        //  遍历每个知识库
        for (String code : codes) {
            //  相似度比较
            List<String> searchResult = milvusService.search(Collections.singletonList(embeddingResult.getEmbedding()), code);
            for(int i = 1; i <= searchResult.size(); i++){
                builder.append(i).append(searchResult.get(i-1)).append("\n");
            }
        }
        //  构建用户提示词
        return String.format(LLMUtils.buildPrompt(), question.getContent(), builder);
    }

    private Message getAnswer(String ask, String prompt) throws Exception {
        ChatCompletionRequest request = new ChatCompletionRequest();
        logger.info("问：{}", ask);
        request.setModel(Model.GLM_3_5_TURBO); // chatGLM_6b_SSE、chatglm_lite、chatglm_lite_32k、chatglm_std、chatglm_pro
        request.setTools(new ArrayList<ChatCompletionRequest.Tool>() {
            private static final long serialVersionUID = -7988151926241837899L;
            {
                add(ChatCompletionRequest.Tool.builder()
                        .type(ChatCompletionRequest.Tool.Type.web_search)
                        .webSearch(ChatCompletionRequest.Tool.WebSearch.builder().enable(true).searchQuery("c++").build())
                        .build());
            }
        });
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;
            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Role.system.getCode())
                        .content(prompt)
                        .build());
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Role.user.getCode())
                        .content(ask)
                        .build());
            }
        });
        ChatCompletionSyncResponse response = openAiSession.completionsSync(request);
        ChatCompletionSyncResponse.Choice choice = response.getChoices().get(0);
        return new Message(choice.getMessage().getContent(), choice.getMessage().getRole());
    }
}
