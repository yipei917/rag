package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.controller.vo.MessageVo;
import com.edu.xmu.rag.controller.vo.PromptVo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.service.ManagementService;
import com.edu.xmu.rag.service.PromptChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ManagementController {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeController.class);

    private final ManagementService managementService;

    @Autowired
    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping("/prompt")
    public ReturnObject createPrompt(@Validated @RequestBody PromptVo promptVo){
        return managementService.createPrompt(promptVo);
    }

    @PutMapping("/prompt")
    public ReturnObject updatePrompt(@Validated @RequestBody PromptVo promptVo){
        return managementService.updatePrompt(promptVo);
    }

    @DeleteMapping("/prompt/{id}")
    public ReturnObject deletePrompt(@PathVariable Long id){
        return managementService.deletePrompt(id);
    }

    @PutMapping("/prompt/{id}/{type}")
    public ReturnObject changePromptStatus(@PathVariable Long id, @PathVariable Integer type){
        if(type == 0){
            return managementService.disablePrompt(id);
        } else {
            return managementService.enablePrompt(id);
        }
    }

    @GetMapping("/prompt/{userId}/{modelId}/{code}")
    public ReturnObject findPrompt(@PathVariable Long userId, @PathVariable Long modelId, @PathVariable String code){
        return managementService.findPrompt(userId, modelId, code);
    }

    @GetMapping("/prompt/{id}")
    public ReturnObject findPromptById(@PathVariable Long id){
        return managementService.findPromptById(id);
    }

    @GetMapping("/prompt")
    public ReturnObject findAllPrompts(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize){
        return new ReturnObject(managementService.findAllPrompts(page, pageSize));
    }

    @GetMapping("/userPrompt/{userId}/{modelId}")
    public ReturnObject getUserPrompt(@PathVariable Long userId, @PathVariable Long modelId){
        return managementService.getUserPrompts(userId, modelId);
    }
}
