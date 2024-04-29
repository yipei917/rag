package com.edu.xmu.rag.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.alibaba.fastjson.JSON;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.dao.bo.ChatData;
import com.edu.xmu.rag.dao.bo.Message;
import com.edu.xmu.rag.llm.LLMUtils;
import com.edu.xmu.rag.llm.result.ZhipuEmbeddingResult;
import com.edu.xmu.rag.llm.result.ZhipuResult;
import com.edu.xmu.rag.service.dto.ChunkResult;
import io.milvus.client.MilvusServiceClient;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.grpc.DataType;
import io.milvus.grpc.SearchResults;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.collection.*;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.response.SearchResultsWrapper;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MilvusService {

    private static final Logger logger = LoggerFactory.getLogger(MilvusService.class);

    @Autowired
    private MilvusServiceClient milvusClient;

    @Value("${llm.zhipuKey}")
    private String apiKey;
    private static final int MAX_LENGTH = 200;


    public void save(String content, String title) {

        List<ChunkResult> chunks = this.textChunk(content, title);

        List<InsertParam.Field> fields = this.embedding(chunks);

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(title)
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

    private List<InsertParam.Field> embedding(List<ChunkResult> chunks) {
        List<String> ans = new ArrayList<>();
        List<Integer> contentWordCount = new ArrayList<>();
        List<List<Float>> contentVector = new ArrayList<>();

        logger.info("start embedding,size:{}", CollectionUtil.size(chunks));
        for (ChunkResult chunk : chunks) {
            logger.info(chunk.getContent());
            ans.add(chunk.getContent());
            contentWordCount.add(chunk.getContent().length());
            ZhipuEmbeddingResult result = this.embedding(chunk);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            List<Float> vector = new ArrayList<>();
            contentVector.add(result.getEmbedding());
        }

        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field("content", ans));
        fields.add(new InsertParam.Field("content_word_count", contentWordCount));
        fields.add(new InsertParam.Field("content_vector", contentVector));

        return fields;
    }

    public void createKnowledgeBase(String title) {
        createCollection(title);
        buildIndex(title);
        logger.info("create Mlivus collection name = {}", title);
    }

    public ZhipuEmbeddingResult embedding(ChunkResult chunk) {
        //log.info("zp-key:{}",apiKey);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .writeTimeout(20000, TimeUnit.MILLISECONDS)
                .addInterceptor(new ZhipuHeaderInterceptor(apiKey));
        OkHttpClient okHttpClient = builder.build();
        ZhipuEmbeddingResult embedRequest=new ZhipuEmbeddingResult();
        embedRequest.setPrompt(chunk.getContent());
        embedRequest.setRequestId(Objects.toString(chunk.getChunkId()));
        // 智谱embedding
        Request request = new Request.Builder()
                .url("https://open.bigmodel.cn/api/paas/v3/model-api/text_embedding/invoke")
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), JSON.toJSONString(embedRequest)))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            ZhipuResult zhipuResult = JSON.parseObject(result, ZhipuResult.class);
            ZhipuEmbeddingResult ret= zhipuResult.getData();
            ret.setPrompt(embedRequest.getPrompt());
            ret.setRequestId(embedRequest.getRequestId());
            return  ret;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> search(List<List<Float>> search_vectors) {
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
        List<String> res = new ArrayList<>();
        if(respSearch.getStatus() == R.Status.Success.getCode()){
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
                res.add(((List<String>) wrapperSearch.getFieldData("content", 0)).get(0));
            }

        }
        milvusClient.releaseCollection(
                ReleaseCollectionParam.newBuilder()
                        .withCollectionName("chat_data")
                        .build());
        return res;
    }

    private void buildIndex(String title){
        final String INDEX_PARAM = "{\"nlist\":1024}";
        milvusClient.createIndex(
                CreateIndexParam.newBuilder()
                        .withCollectionName(title)
                        .withFieldName("content_vector")
                        .withIndexType(IndexType.IVF_FLAT)
                        .withMetricType(MetricType.L2)
                        .withExtraParam(INDEX_PARAM)
                        .withSyncMode(Boolean.FALSE)
                        .build()
        );
    }
    public void dropCollection(String title){
        milvusClient.dropCollection(
                DropCollectionParam.newBuilder()
                        .withCollectionName(title)
                        .build()
        );
    }
    private void createCollection(String title){
        FieldType fieldType1 = FieldType.newBuilder()
                .withName("id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(true)
                .build();
        FieldType fieldType2 = FieldType.newBuilder()
                .withName("content_word_count")
                .withDataType(DataType.Int32)
                .build();
        FieldType fieldType3 = FieldType.newBuilder()
                .withName("content")
                .withDataType(DataType.VarChar)
                .withMaxLength(1024)
                .build();
        FieldType fieldType4 = FieldType.newBuilder()
                .withName("content_vector")
                .withDataType(DataType.FloatVector)
                .withDimension(1024)
                .build();
        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(title)
                .withShardsNum(4)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .addFieldType(fieldType3)
                .addFieldType(fieldType4)
                .build();
        milvusClient.createCollection(createCollectionReq);
    }

    private List<ChunkResult> textChunk(String content, String title) {
        String text = content.replaceAll("\\s", " ").replaceAll("(\\r\\n|\\r|\\n|\\n\\r)"," ");
        String[] sentence = text.split("。");
        List<String> lines = new ArrayList<>();
        for (String s : sentence) {
            if (s.length() > MAX_LENGTH) {
                for (int index = 0; index < sentence.length; index += MAX_LENGTH) {
                    // 计算当前子串的结束索引
                    int endIndex = Math.min(index + MAX_LENGTH, sentence.length);
                    // 提取子串
                    String substring = s.substring(index, endIndex);
                    // 检查子串长度是否满足要求
                    if (substring.length() >= 5) {
                        lines.add(substring); // 将符合要求的子串加入列表
                    }
                }
            } else {
                lines.add(s);
            }
        }
        List<ChunkResult> chunks = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (String line:lines) {
            ChunkResult chunkResult = new ChunkResult();
            chunkResult.setDocId(title);
            chunkResult.setContent(line);
            chunkResult.setChunkId(atomicInteger.incrementAndGet());
            chunks.add(chunkResult);
        }
        return chunks;
    }

    @AllArgsConstructor
    private static class ZhipuHeaderInterceptor implements Interceptor {

        final String apiKey;

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request original = chain.request();
            String authorization = LLMUtils.gen(apiKey,60);
            //log.info("authorization:{}",authorization);
            Request request = original.newBuilder()
                    .header(Header.AUTHORIZATION.getValue(), authorization)
                    .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        }
    }
}
