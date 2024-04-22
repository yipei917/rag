package com.edu.xmu.rag.dao;

import com.edu.xmu.rag.controller.vo.PromptVo;
import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import com.edu.xmu.rag.core.util.Common;
import com.edu.xmu.rag.dao.bo.Prompt;
import com.edu.xmu.rag.mapper.PromptPoMapper;
import com.edu.xmu.rag.mapper.po.PromptPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.edu.xmu.rag.core.model.Constants.MAX_RETURN;
import static com.edu.xmu.rag.core.util.Common.cloneObj;
import static com.edu.xmu.rag.core.util.Common.putGmtFields;

@Repository
public class PromptDao {
    private final static Logger logger = LoggerFactory.getLogger(PromptDao.class);

    private final PromptPoMapper promptPoMapper;

    private final UserDao userDao;

    private final ModelDao modelDao;

    @Autowired
    public PromptDao(PromptPoMapper promptPoMapper, UserDao userDao, ModelDao modelDao) {
        this.promptPoMapper = promptPoMapper;
        this.userDao = userDao;
        this.modelDao = modelDao;
    }

    private void setBo(Prompt bo) {
        bo.setUserDao(this.userDao);
        bo.setModelDao(this.modelDao);
    }

    private Prompt getBo(PromptPo po) {
        Prompt ret = cloneObj(po, Prompt.class);
        this.setBo(ret);
        return ret;
    }

    public Optional<Prompt> findPromptById(Long id) throws RuntimeException{
        AtomicReference<Prompt> prompt = new AtomicReference<>();
        if (null == id) {
            return null;
        }else{
            logger.debug("findObjById: id = {}",id);
            Optional<PromptPo> ret = this.promptPoMapper.findById(id);
            ret.ifPresent(po -> prompt.set(cloneObj(po, Prompt.class)));
            if (null == prompt.get()) {
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST,ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage());
            }
        }
        return Optional.of(prompt.get());
    }

    public Prompt insert(Prompt prompt) throws RuntimeException {
        PromptPo po = cloneObj(prompt, PromptPo.class);
        putGmtFields(po, "create");
        logger.debug("insertPrompt: po = {}", po);
        this.promptPoMapper.save(po);
        return cloneObj(po, Prompt.class);
    }

    public Prompt save(Prompt prompt) {
        PromptPo po = cloneObj(prompt, PromptPo.class);
        putGmtFields(po, "modified");
        logger.debug("savePrompt: po = {}", po);
        PromptPo save = this.promptPoMapper.save(po);
        if (save.getId() == -1) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOT_EXIST, String.format(ReturnNo.RESOURCE_ID_NOT_EXIST.getMessage(), "提示词", prompt.getId()));
        }
        return cloneObj(save, Prompt.class);
    }

    public ReturnObject delById(Long id) {
        this.promptPoMapper.deleteById(id);
        return new ReturnObject(ReturnNo.OK);
    }

    public List<Prompt> retrieveByUserIdAndModelId(Long UserId, Long ModelId) throws RuntimeException {
        if (null == UserId || null == ModelId) {
            return null;
        }

        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<PromptPo> ret = promptPoMapper.findPromptPoByUserIdAndModelId(UserId, ModelId, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            return ret.stream().map(po -> cloneObj(po, Prompt.class)).toList();
        }
    }

    public List<Prompt> retrieveByUserId(Long UserId) throws RuntimeException {
        if (null == UserId) {
            return null;
        }

        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<PromptPo> ret = promptPoMapper.findPromptPoByUserId(UserId, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            return ret.stream().map(po -> cloneObj(po, Prompt.class)).toList();
        }
    }

    public List<Prompt> retrieveByModelId(Long ModelId) throws RuntimeException {
        if (null == ModelId) {
            return null;
        }

        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<PromptPo> ret = promptPoMapper.findPromptPoByModelId(ModelId, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            return ret.stream().map(po -> cloneObj(po, Prompt.class)).toList();
        }
    }

    public List<Prompt> retrieveAll() throws RuntimeException {
        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<PromptPo> ret = promptPoMapper.findAll(pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            return ret.stream().map(po -> cloneObj(po, Prompt.class)).toList();
        }
    }

    public List<Prompt> findAllPrompts(Integer page, Integer pageSize) throws RuntimeException{
        Pageable pageable = PageRequest.of(page-1,pageSize);
        Page<PromptPo> pos = promptPoMapper.findAll(pageable);
        return pos.stream().map((po)->{
            Prompt bo= Common.cloneObj(po,Prompt.class);
            return bo;
        }).collect(Collectors.toList());
    }

}
