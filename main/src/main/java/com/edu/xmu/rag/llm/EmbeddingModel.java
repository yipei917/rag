package com.edu.xmu.rag.llm;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.edu.xmu.rag.llm.param.EmbeddingsApiParam;
import com.edu.xmu.rag.llm.result.EmbeddingsApiResult;

@Component
public class EmbeddingModel {
    @Autowired
    private EmbeddingsApiParam embeddingsApiParam = new EmbeddingsApiParam();

    @Value("${gpt.model.key}")
    private String apiKey;

    /**
     * 请求获取Embeddings，请求出错返回null
     *
     * @param apiKey openAIKey
     * @param msg    需要Embeddings的信息
     * @return 为null则请求失败，反之放回正确结果
     */
    public EmbeddingsApiResult doEmbedding(String apiKey, String msg) {
        this.embeddingsApiParam.setInput(msg);
        Gson gson = new Gson();
        String json = gson.toJson(this.embeddingsApiParam);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/embeddings")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(requestBody).build();
        EmbeddingsApiResult embeddingsApiResult = null;
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                embeddingsApiResult = gson.fromJson(response.body().string(), EmbeddingsApiResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return embeddingsApiResult;
    }
}
