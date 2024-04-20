package com.edu.xmu.rag.config;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.http.HttpClient;


@Configuration
public class DefaultConfig {

    @Value("${milvus.ip}")
    private String milvusIp;
    @Value("${milvus.port}")
    private int milvusPort;

    @Bean
    public MilvusServiceClient milvusClient(){
        return new MilvusServiceClient(
                ConnectParam.newBuilder()
                        .withHost(milvusIp)
                        .withPort(milvusPort)
                        .build()
        );
    }
}