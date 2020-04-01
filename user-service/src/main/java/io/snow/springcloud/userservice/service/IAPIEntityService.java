package io.snow.springcloud.userservice.service;

import io.snow.model.vo.ApiVo;

import java.util.List;

public interface IAPIEntityService {
    int createApiEntity(ApiVo apiVo);
    ApiVo getApiVoByPath(String path);

    List<ApiVo> queryApiByDesc(String desc);
}
