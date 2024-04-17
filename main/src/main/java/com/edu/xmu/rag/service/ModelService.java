package com.edu.xmu.rag.service;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.ModelDao;
import com.edu.xmu.rag.dao.bo.KnowledgeBase;
import com.edu.xmu.rag.dao.bo.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Service
@Transactional
public class ModelService {
    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    private final ModelDao modelDao;

    @Autowired
    public ModelService(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    public ReturnObject getModelById(Long id) {
        Model model = modelDao.findModelById(id);
        if (model != null) {
            return new ReturnObject(ReturnNo.OK, model);
        } else {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOT_EXIST);
        }
    }

    public ReturnObject createModel(Model model) {
        return new ReturnObject(ReturnNo.CREATED, modelDao.insert(model));
    }

    public ReturnObject updateModel(Model model) {
        return new ReturnObject(modelDao.save(model));
    }

    public ReturnObject deleteModelById(Model model) {
        Model ret = modelDao.findModelById(model.getId());
        if(ret != null)
        {
            modelDao.delById(model);
            return new ReturnObject(ReturnNo.OK);
        }else {
            throw new BusinessException(ReturnNo.USER_INVALID_ACCOUNT);
        }
    }

    public ReturnObject getAllModels() {
        List<Model> res = modelDao.findAllModels();
        return new ReturnObject(res);
    }

    public ReturnObject enableModel(Long id) {
        Model model = modelDao.findModelById(id);
        if(model.getStatus() == 0)
        {
            model.setStatus(1);
            modelDao.save(model);
            return new ReturnObject(ReturnNo.OK);
        }else{
            return new ReturnObject(ReturnNo.STATE_NOT_ALLOW);
        }
    }

    public ReturnObject disableModel(Long id) {
        Model model = modelDao.findModelById(id);
        if(model.getStatus() == 1)
        {
            model.setStatus(0);
            modelDao.save(model);
            return new ReturnObject(ReturnNo.OK);
        }else{
            return new ReturnObject(ReturnNo.STATE_NOT_ALLOW);
        }
    }
}
