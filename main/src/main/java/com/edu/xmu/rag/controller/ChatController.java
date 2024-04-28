package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.controller.vo.ChatVo;
import com.edu.xmu.rag.controller.vo.MessageVo;
import com.edu.xmu.rag.controller.vo.SimpleText;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.Chat;
import com.edu.xmu.rag.service.ChatService;
import com.edu.xmu.rag.service.IChatService;
import com.edu.xmu.rag.service.ManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private IChatService chatServiceImpl;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/message/{id}")
    public ReturnObject getMessages(@PathVariable Long id) {
        return chatService.findMessageListByChatId(id);
    }

    @GetMapping("/chatList/{id}")
    public ReturnObject getChatList(@PathVariable Long id) {
        return chatService.findChatListByUserId(id);
    }

    @PostMapping("/chat")
    public ReturnObject createChat(@Validated @RequestBody Chat vo) {
        return chatService.createChat(vo);
    }

    @PutMapping("/chat")
    public ReturnObject updateChat(@Validated @RequestBody ChatVo vo) {
        return chatService.updateChat(vo);
    }

    @DeleteMapping("/chat/{id}")
    public ReturnObject delChat(@PathVariable Long id) {
        return chatService.delChat(id);
    }

    @PostMapping("/message")
    public ReturnObject chat(@Validated @RequestBody MessageVo vo) {
        return chatService.chat(vo);
    }

    //向ChatGPT提问
    @GetMapping("/sendquestion")
    public String chat(@RequestParam String question, @RequestParam Long id){
        String prompt = managementService.findPromptContentById(id);
        logger.info(prompt);
        return chatServiceImpl.toChat(question, prompt);
    }

    @GetMapping("/txt2pic")
    public ReturnObject txt2pic(@Validated @RequestBody SimpleText text) {
        return chatService.txt2pic(text.getContent());
    }
}
