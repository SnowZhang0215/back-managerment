package io.snow.springcloud.userservice.mapper;

import io.snow.model.vo.RoleVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RoleMapper {
    RoleVo findRoleByRoleName(String roleName);

    List<RoleVo> listRolePageable();

    int insertRole(RoleVo roleVo);

    List<Long> findRolePermissionId(Long Id);

    void deleteRolePermission(Long roleId);

    void insertRolePermission(Map<String,Object> map);
}
