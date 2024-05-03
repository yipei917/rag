package com.edu.xmu.rag.service;

import com.edu.xmu.rag.MainApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class MilvusServiceTest {
}
