package io.snow.springcloud.userservice.service;

import io.snow.model.vo.Permission;

import java.util.List;
import java.util.Map;

public interface IMenuService {

    List<Permission> getUserNavMenus(String userName);

    List<Permission> getAllParentMenus();

    int createPermission(Permission permission);
}
