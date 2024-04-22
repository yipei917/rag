package com.edu.xmu.rag.service;

import com.edu.xmu.rag.controller.vo.PromptVo;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.ModelDao;
import com.edu.xmu.rag.dao.PromptDao;
import com.edu.xmu.rag.dao.bo.Prompt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Service
@Transactional
public class ManagementService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final PromptDao promptDao;

    private final ModelDao modelDao;

    @Autowired
    ManagementService(PromptDao promptDao, ModelDao modelDao) {
        this.promptDao = promptDao;
        this.modelDao = modelDao;
    }

    /**
     * 新增提示词
     */
    public ReturnObject createPrompt(PromptVo promptVo){
        Prompt ret = cloneObj(promptVo, Prompt.class);
        ret.setStatus(1);
        return new ReturnObject(promptDao.insert(ret));
    }

    /**
     * 更新提示词
     */
    public ReturnObject updatePrompt(PromptVo promptVo){
        Prompt ret = cloneObj(promptVo, Prompt.class);
        return new ReturnObject(promptDao.save(ret));
    }

    /**
     * 删除提示词
     */
    public ReturnObject deletePrompt(Long id){
        return promptDao.delById(id);
    }

    /**
     * 启用提示词
     */
    public ReturnObject enablePrompt(Long id){
        Prompt ret = promptDao.findPromptById(id).get();
        ret.setStatus(1);
        promptDao.save(ret);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 禁用提示词
     */
    public ReturnObject disablePrompt(Long id){
        Prompt ret = promptDao.findPromptById(id).get();
        ret.setStatus(0);
        promptDao.save(ret);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     *根据用户id、模型id和提示词code查询提示词
     */
    public ReturnObject findPrompt(Long userId, Long modelId,String code) throws RuntimeException {
        List<Prompt> list;
        if (0 != userId && 0 != modelId) {
            list = promptDao.retrieveByUserIdAndModelId(userId, modelId);
        } else if(0 != userId) {
            list = promptDao.retrieveByUserId(userId);
        } else if(0 != modelId){
            list = promptDao.retrieveByModelId(modelId);
        } else {
            list = promptDao.retrieveAll();
        }
        if(list.isEmpty()){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOT_EXIST, "该提示词不存在！");
        } else {
            if (code.equals("*")) {
                return new ReturnObject(list);
            } else {
                return new ReturnObject(list.stream().filter(po -> po.getCode().equals(code)).toList());
            }
        }
    }

    public ReturnObject findPromptById(Long id) throws RuntimeException {
        Prompt prompt = promptDao.findPromptById(id).get();
        if(prompt != null){
            return new ReturnObject(ReturnNo.OK, prompt);
        } else {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "提示词", id));
        }
    }

    public String findPromptContentById(Long id) throws RuntimeException {
        Optional<Prompt> optionalPrompt = promptDao.findPromptById(id);
        if (optionalPrompt.isPresent()) {
            Prompt prompt = optionalPrompt.get(); // 获取实际的 Prompt 对象
            return prompt.getSystemPrompt(); // 返回提示词内容
        } else {
            // 如果未找到对应的提示词，可以抛出异常或者返回默认值
            throw new RuntimeException("Prompt not found for ID: " + id);
        }
    }

}