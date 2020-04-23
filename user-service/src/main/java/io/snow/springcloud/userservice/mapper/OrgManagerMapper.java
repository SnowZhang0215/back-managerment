package io.snow.springcloud.userservice.mapper;

import io.snow.model.vo.OrgVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrgManagerMapper {
    int insertOrg(OrgVo orgVo);

    int updateOrgVo(OrgVo orgVo);

    List<OrgVo> listOrgPageable();

    List<OrgVo> findOrgLikeName(String orgName);

    OrgVo selectByOrgId(String orgId);
}
