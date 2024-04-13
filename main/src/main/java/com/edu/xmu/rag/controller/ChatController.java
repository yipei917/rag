package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.controller.vo.MessageVo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/message/{id}")
    public ReturnObject getMessages(@PathVariable Long id) {
        return chatService.findMessageListByChatId(id);
    }
}
