package io.snow.springcloud.userservice.service;

import io.snow.model.vo.OrgMemberVo;

public interface IFightChangeService {

    int insertFightChange(OrgMemberVo orgMemberVo);

    Long getRecentFightVal(Long id);
}
