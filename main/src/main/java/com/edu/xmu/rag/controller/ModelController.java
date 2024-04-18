package com.edu.xmu.rag.controller;

import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.Model;
import com.edu.xmu.rag.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ModelController {
    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    //根据id查找模型
    @GetMapping("/model/{id}")
    public ReturnObject getModelById(@PathVariable Long id) {
        return modelService.getModelById(id);
    }

    //新建模型
    @PostMapping("/model/new")
    public ReturnObject createModel(@RequestBody Model model) {
        return modelService.createModel(model);
    }

    @PutMapping("/model")
    public ReturnObject updateModel(@RequestBody Model model) {
        return modelService.updateModel(model);
    }

    //逻辑删除
    @DeleteMapping("/model")
    public ReturnObject deleteModelById(@RequestBody Model model) {
        return modelService.deleteModelById(model);
    }

    @GetMapping("/modelList")
    public ReturnObject getAllModels() {
        return modelService.getAllModels();
    }

    @PutMapping("/model/{id}/{type}")
    public ReturnObject changeModelStatus(@PathVariable Long id, @PathVariable Integer type)
    {
        if (type == 0) {
            return modelService.disableModel(id);
        } else {
            return modelService.enableModel(id);
        }
    }
}
