package io.snow.springcloud.userservice.mapper;

import io.snow.model.vo.RoleVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {
    RoleVo findRoleByRoleName(String roleName);
}
