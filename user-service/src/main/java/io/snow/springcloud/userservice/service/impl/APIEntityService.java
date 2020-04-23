package io.snow.springcloud.userservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.snow.model.vo.ApiVo;
import io.snow.rest.common.page.PageRequest;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.mapper.APIMapper;
import io.snow.springcloud.userservice.service.IAPIEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<ApiVo> queryApiByDesc(String desc) {
        return apiMapper.selectByDesc(desc);
    }

    @Override
    public PageResult listApiEntity(Integer pageIndex, Integer pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageIndex == null ? 1 : pageIndex);
        pageRequest.setPageSize(pageSize == null ? 10 : pageSize);
        Page<Object> page = PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), true);
        List<ApiVo> list = apiMapper.findApiEntityPageable();
        PageResult result = new PageResult();
        result.setContent(list);
        result.setTotalSize(page.getTotal());
        result.setPageNum(pageRequest.getPageNum());
        result.setPageSize(pageRequest.getPageSize());
        return result;
    }

    @Override
    public int editApiEntity(ApiVo apiVo) {
        return  apiMapper.updateApiVo(apiVo);
    }
}
