package io.snow.springcloud.userservice.service.impl;

import io.snow.model.vo.FightChangeVo;
import io.snow.model.vo.OrgMemberVo;
import io.snow.springcloud.userservice.mapper.FightChangeMapper;
import io.snow.springcloud.userservice.service.IFightChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FightChangeService implements IFightChangeService {

    @Autowired
    private FightChangeMapper fightChangeMapper;

    @Override
    public int insertFightChange(OrgMemberVo orgMemberVo) {
        if (orgMemberVo.getLastModifiedDate() == null){
            orgMemberVo.setLastModifiedDate(Instant.now());
        }
        return fightChangeMapper.insertFightChange(orgMemberVo);
    }

    /**
     * 查询近7天的战斗力变化
     * @param id
     * @return
     */
    @Override
    public Long getRecentFightVal(Long id) {
        long val = 0;
        List<FightChangeVo> result = fightChangeMapper.selectRecentFightValChangeList(id);
        if (result!=null){
            Long min = result.get(0).getFightVal();
            Long max = result.get(result.size()-1).getFightVal();
            val = max-min;
        }
        return val;
    }

    @Override
    public List<FightChangeVo> getRecentChangeData(Long id) {
        return fightChangeMapper.selectRecentFightValChangeList(id);
    }
}
