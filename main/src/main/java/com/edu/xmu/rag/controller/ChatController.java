package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.controller.vo.MessageVo;
import com.edu.xmu.rag.core.model.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @GetMapping("/message")
    public ReturnObject getMessages() {
        List<MessageVo> res = new ArrayList<>();
        MessageVo m1 = new MessageVo();
        m1.setRole(1);
        m1.setContent("11");
        MessageVo m2 = new MessageVo();
        m2.setRole(2);
        m2.setContent("22");
        res.add(m1);
        res.add(m2);
        return new ReturnObject(res);
    }
}
