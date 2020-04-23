package io.snow.springcloud.userservice.mapper;

import io.snow.model.vo.OrgMemberVo;

import java.util.List;

public interface OrgMemberMapper {
    int insertOrgMember(OrgMemberVo orgMemberVo);

    List<OrgMemberVo> listOrgMemberPageable(Long orgId);

    List<OrgMemberVo> listOrgMemberPageableByAdmin();

    int updateOrgMemberVo(OrgMemberVo orgMemberVo);

    OrgMemberVo findOrgMemberByOrgIdAndGameId(OrgMemberVo orgMemberVo);

    OrgMemberVo selectByGameId(String gameId);

    int deleteOrgMemberById(Long id);

    OrgMemberVo getMemberInfo(String userName);
}
