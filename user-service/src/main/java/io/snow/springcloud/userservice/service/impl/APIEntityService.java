package io.snow.springcloud.userservice.service.impl;

import io.snow.model.vo.ApiVo;
import io.snow.springcloud.userservice.mapper.APIMapper;
import io.snow.springcloud.userservice.service.IAPIEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class APIEntityService implements IAPIEntityService {

    @Autowired
    private APIMapper apiMapper;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public int createApiEntity(ApiVo apiVo) {
        return apiMapper.insertApiEntity(apiVo);
    }

    @Override
    public ApiVo getApiVoByPath(String path) {
        return apiMapper.selectByPath(path);
    }
}
