package io.snow.springcloud.userservice.mapper;

import io.snow.model.vo.ApiVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface APIMapper {
    ApiVo selectById(Long id);

    ApiVo selectByPath(String path);

    int insertApiEntity(ApiVo apiVo);

    List<ApiVo> selectByDesc(String desc);
}
