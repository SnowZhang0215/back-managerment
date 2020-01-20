package io.snow.springcloud.userservice.service;

import io.snow.model.vo.Permission;

import java.util.List;

public interface IMenuService {

    List<Permission> getUserNavMenus(String userName);

    List<Permission> getAllParentMenus();
}
