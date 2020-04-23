package io.snow.springcloud.userservice.service;

import io.snow.model.vo.FightChangeVo;
import io.snow.model.vo.OrgMemberVo;

import java.util.List;

public interface IFightChangeService {

    int insertFightChange(OrgMemberVo orgMemberVo);

    Long getRecentFightVal(Long id);

    List<FightChangeVo> getRecentChangeData(Long id);
}
