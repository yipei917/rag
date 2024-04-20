package com.edu.xmu.rag.service;

import io.milvus.client.MilvusServiceClient;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.grpc.SearchResults;
import io.milvus.param.R;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.collection.ReleaseCollectionParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.response.SearchResultsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.xmu.rag.dao.bo.ChatData;
import com.edu.xmu.rag.llm.ChatGptModel;
import com.edu.xmu.rag.llm.EmbeddingModel;
import com.edu.xmu.rag.llm.result.EmbeddingsApiResult;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatServiceImpl implements IChatService{
    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);
    @Autowired
    private MilvusServiceClient milvusClient;
    @Autowired
    private ChatGptModel chatGptModel;
    @Autowired
    private EmbeddingModel embeddingModel;


    public String toChat(String key,String question){
        EmbeddingsApiResult embedding = embeddingModel.doEmbedding(key,question);
        if(embedding == null) return "请求失败！";
        List<Float> vector = embedding.getData().get(0).getEmbedding();
        List<ChatData> searchResult = search(Arrays.asList(vector));
        List<String> contents = new ArrayList<>();
        for(ChatData data:searchResult){
            contents.add(data.getContent());
        }
        String ans = chatGptModel.doChat(key,question, contents);
        return ans;
    }


    public List<ChatData> search(List<List<Float>> search_vectors){
        milvusClient.loadCollection(
                LoadCollectionParam.newBuilder()
                        .withCollectionName("chat_data")
                        .build()
        );

        final Integer SEARCH_K = 4;                       // TopK
        final String SEARCH_PARAM = "{\"nprobe\":10}";    // Params
        List<String> ids = Arrays.asList("id");
        List<String> contents = Arrays.asList("content");
        List<String> contentWordCounts = Arrays.asList("content_word_count");
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName("chat_data")
                .withConsistencyLevel(ConsistencyLevelEnum.STRONG)
                .withOutFields(ids)
                .withOutFields(contents)
                .withOutFields(contentWordCounts)
                .withTopK(SEARCH_K)
                .withVectors(search_vectors)
                .withVectorFieldName("content_vector")
                .withParams(SEARCH_PARAM)
                .build();
        R<SearchResults> respSearch = milvusClient.search(searchParam);
        List<ChatData> chatDataList = new ArrayList<>();
        if(respSearch.getStatus() == R.Status.Success.getCode()){
            //respSearch.getData().getStatus() == R.Status.Success
            SearchResults resp = respSearch.getData();
            if(!resp.hasResults()){ //判断是否查到结果
                return new ArrayList<>();
            }
            for (int i = 0; i < search_vectors.size(); ++i) {
                SearchResultsWrapper wrapperSearch = new SearchResultsWrapper(resp.getResults());
                List<Long> id = (List<Long>) wrapperSearch.getFieldData("id", 0);
                List<String> content = (List<String>) wrapperSearch.getFieldData("content", 0);
                List<Integer> contentWordCount = (List<Integer>) wrapperSearch.getFieldData("content_word_count", 0);
                ChatData chatData = new ChatData(id.get(0),content.get(0),contentWordCount.get(0));
                chatDataList.add(chatData);

            }

        }
        milvusClient.releaseCollection(
                ReleaseCollectionParam.newBuilder()
                        .withCollectionName("chat_data")
                        .build());
        return chatDataList;
    }


    private static final int MAX_LENGTH = 200;

    public void save(String text){
        //过滤字符
        text = text.replaceAll("\\s", " ").replaceAll("(\\r\\n|\\r|\\n|\\n\\r)"," ");
        String[] sentence = text.split("。");
        List<String> ans = new ArrayList<>();
        for (String s : sentence) {
            if (s.length() > MAX_LENGTH) {
                for (int index = 0; index < sentence.length; index += MAX_LENGTH) {
                    // 计算当前子串的结束索引
                    int endIndex = Math.min(index + MAX_LENGTH, sentence.length);
                    // 提取子串
                    String substring = s.substring(index, endIndex);
                    // 检查子串长度是否满足要求
                    if (substring.length() >= 5) {
                        ans.add(substring); // 将符合要求的子串加入列表
                    }
                }
            } else {
                ans.add(s);
            }
        }

        List<Integer> contentWordCount = new ArrayList<>();
        List<List<Float>> contentVector = new ArrayList<>();
        String key = "";
        for(String str : ans){
            logger.info(str);
            contentWordCount.add(str.length());
            EmbeddingsApiResult embedding = embeddingModel.doEmbedding(key,str);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(embedding == null){
                break;
            }
            contentVector.add(embedding.getData().get(0).getEmbedding());
        }

        System.out.println(ans.size());
        System.out.println(contentWordCount.size());
        System.out.println(contentVector.size());

        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field("content", ans));
        fields.add(new InsertParam.Field("content_word_count", contentWordCount));
        fields.add(new InsertParam.Field("content_vector", contentVector));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName("chat_data")
                .withFields(fields)
                .build();
        logger.info("inserting...");
        //插入数据
        try {
            milvusClient.insert(insertParam);
            logger.info("Insert operation succeeded.");
        } catch (Exception e) {
            logger.info("error.");
            e.printStackTrace();
        }

    }

}
