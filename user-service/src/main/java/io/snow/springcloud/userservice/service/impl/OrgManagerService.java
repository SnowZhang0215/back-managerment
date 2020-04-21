package io.snow.springcloud.userservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.snow.model.vo.OrgVo;
import io.snow.rest.common.page.PageRequest;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.mapper.OrgManagerMapper;
import io.snow.springcloud.userservice.service.IOrgManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrgManagerService implements IOrgManagerService {

    @Autowired
    private OrgManagerMapper orgManagerMapper;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public int createOrg(OrgVo orgVo) {
        return orgManagerMapper.insertOrg(orgVo);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public int editOgrInfo(OrgVo orgVo) {
        return orgManagerMapper.updateOrgVo(orgVo);
    }

    @Override
    public PageResult listOrg(Integer pageIndex, Integer pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageIndex == null ? 1 : pageIndex);
        pageRequest.setPageSize(pageSize == null ? 10 : pageSize);
        Page<Object> page = PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), true);
        List<OrgVo> list = orgManagerMapper.listOrgPageable();
        PageResult result = new PageResult();
        result.setContent(list);
        result.setTotalSize(page.getTotal());
        result.setPageNum(pageRequest.getPageNum());
        result.setPageSize(pageRequest.getPageSize());
        return result;
    }

    @Override
    public List<OrgVo> queryOrgByName(String orgName) {
        return orgManagerMapper.findOrgLikeName(orgName);
    }

    @Override
    public OrgVo getOrgByOrgId(String orgId) {
        return orgManagerMapper.selectByOrgId(orgId);
    }
}
