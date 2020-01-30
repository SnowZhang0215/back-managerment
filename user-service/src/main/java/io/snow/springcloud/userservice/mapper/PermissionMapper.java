package io.snow.springcloud.userservice.mapper;

import io.snow.model.vo.Permission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PermissionMapper {

    List<Permission> getUserNavPermissions(String userName);

    List<Permission> getDefaultPermission();

    List<Long> getUserPermissionsIds(String userName);

    List<Permission> findAllParentMenus();

    int insertPermission(Permission permission);
}
