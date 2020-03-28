package io.snow.springcloud.userservice.service;

import io.snow.model.vo.Permission;
import io.snow.rest.common.page.PageResult;

import java.util.List;
import java.util.Map;

public interface IMenuService {

    List<Permission> getUserNavMenus(String userName);

    List<Permission> getAllParentMenus();

    int createPermission(Permission permission);

    PageResult getSubMenuByParentId(Map<String, Object> map);

    Permission getPermissionDetail(Long id);
}
