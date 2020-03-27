package io.snow.springcloud.userservice.service;

import io.snow.model.vo.ApiVo;

public interface IAPIEntityService {
    int createApiEntity(ApiVo apiVo);
    ApiVo getApiVoByPath(String path);
}
