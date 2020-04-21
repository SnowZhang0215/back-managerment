package io.snow.springcloud.userservice.service;

import io.snow.model.vo.OrgVo;
import io.snow.rest.common.page.PageResult;

import java.util.List;

public interface IOrgManagerService {
    int editOgrInfo(OrgVo orgVo);

    int createOrg(OrgVo orgVo);

    PageResult listOrg(Integer pageIndex, Integer pageSize);

    List<OrgVo> queryOrgByName(String orgName);

    OrgVo getOrgByOrgId(String orgId);
}
