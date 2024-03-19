package com.edu.xmu.rag;

import com.edu.xmu.rag.core.jpa.SelectiveUpdateJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.edu.xmu.rag.core",
        "com.edu.xmu.rag"})
@EnableJpaRepositories(value = "com.edu.xmu.rag.core.jpa",
        repositoryBaseClass = SelectiveUpdateJpaRepositoryImpl.class, basePackages = "com.edu.xmu.rag.mapper")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
