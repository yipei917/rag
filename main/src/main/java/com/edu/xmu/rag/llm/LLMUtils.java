package com.edu.xmu.rag.llm;

import cn.hutool.jwt.JWTUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LLMUtils {


    public static String buildPrompt(){
        return """
                请利用如下上下文的信息回答问题：
                "{%s}"
                上下文信息如下：
                "{%s}"
                如果上下文信息中没有帮助,则不允许胡乱回答！""";
    }

    public static String gen(String apiKey, int expSeconds) {
        String[] parts = apiKey.split("\\.");
        if (parts.length != 2) {
            throw new RuntimeException("智谱invalid key");
        }

        String id = parts[0];
        String secret = parts[1];
        Map<String, Object> payload = new HashMap<>();
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + (60 * 1000);
        payload.put("api_key", id);
        payload.put("exp", expirationTimeMillis);
        payload.put("timestamp", currentTimeMillis);
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("sign_type", "SIGN");
        return JWTUtil.createToken(headerMap,payload,secret.getBytes(StandardCharsets.UTF_8));
    }


}
