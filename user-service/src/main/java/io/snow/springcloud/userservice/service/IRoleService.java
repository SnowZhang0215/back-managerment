package io.snow.springcloud.userservice.service;

import io.snow.model.vo.RoleVo;
import io.snow.rest.common.page.PageResult;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    PageResult getAllRoles(Integer pageIndex, Integer pageSize);

    int createRole(RoleVo roleVo);

    int deleteRoles(List<Long> deleteIds);

    RoleVo getRoleById(Long id);

    Map<String,Object> authRolePermission(Map<String,Object> map);

    List<Long> getRolePermission(Long rId);

    List<RoleVo> getAll();

}
