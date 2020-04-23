package io.snow.springcloud.userservice.service;

import io.snow.model.vo.ApiVo;
import io.snow.rest.common.page.PageResult;

import java.util.List;

public interface IAPIEntityService {
    int createApiEntity(ApiVo apiVo);
    ApiVo getApiVoByPath(String path);

    List<ApiVo> queryApiByDesc(String desc);

    PageResult listApiEntity(Integer pageIndex, Integer pageSize);

    int editApiEntity(ApiVo apiVo);
}
