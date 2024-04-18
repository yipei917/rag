package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.dao.bo.Model;
import com.edu.xmu.rag.mapper.ModelPoMapper;
import com.edu.xmu.rag.mapper.po.ModelPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.edu.xmu.rag.core.util.Common.cloneObj;

@Repository
public class ModelDao {
    private final static Logger logger = LoggerFactory.getLogger(ModelDao.class);

    private final ModelPoMapper modelPoMapper;

    @Autowired
    public ModelDao(ModelPoMapper modelPoMapper) {
        this.modelPoMapper = modelPoMapper;
    }

    public Model findModelById(Long id) {
        if (null == id) {
            return null;
        }

        Optional<ModelPo> po = modelPoMapper.findById(id);
        if (po.isPresent()) {
            return cloneObj(po.get(), Model.class);
        } else {
            throw new BusinessException(ReturnNo.AUTH_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "模型", id));
        }
    }

    public Model insert(Model model) throws RuntimeException {
        ModelPo po = cloneObj(model, ModelPo.class);
        logger.debug("insertModel: po = {}", po);
        this.modelPoMapper.save(po);
        return cloneObj(po, Model.class);
    }

    public Model save(Model model) {
        ModelPo po = cloneObj(model, ModelPo.class);
        logger.debug("saveModel: po = {}", po);
        ModelPo save = this.modelPoMapper.save(po);
        if (save.getId() == -1) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "模型", model.getId()));
        }
        return cloneObj(save, Model.class);
    }

    public ReturnObject delById(Model model) {
        modelPoMapper.deleteById(model.getId());
        return new ReturnObject(ReturnNo.OK);
    }

    public List<Model> findAllModels() {
        List<ModelPo> modelPos = modelPoMapper.findAll();
        return modelPos.stream()
                .map(modelPo -> cloneObj(modelPo, Model.class))
                .collect(Collectors.toList());
    }
}
