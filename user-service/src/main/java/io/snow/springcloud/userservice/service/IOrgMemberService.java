package io.snow.springcloud.userservice.service;

import io.snow.model.vo.OrgMemberVo;
import io.snow.rest.common.page.PageResult;

public interface IOrgMemberService {
    int updateOrgMember(OrgMemberVo orgMemberVo) throws Exception;

    OrgMemberVo getOrgMemberByOrgIdAndMemberCode(OrgMemberVo orgMemberVo);

    int createOrgMember(OrgMemberVo orgMemberVo) throws Exception;

    PageResult listOrgMember(Integer pageIndex, Integer pageSize, Integer isAdmin, String orgId) throws Exception;

    OrgMemberVo getOrgMemberByGameId(OrgMemberVo orgMemberVo);

    int deleteOrgMember(Long id);
}
