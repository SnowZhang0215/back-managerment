package io.snow.springcloud.userservice.mapper;

import io.snow.model.vo.FightChangeVo;
import io.snow.model.vo.OrgMemberVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FightChangeMapper {
    int insertFightChange(OrgMemberVo orgMemberVo);

    List<FightChangeVo> selectRecentFightValChangeList(Long memberId);
}
