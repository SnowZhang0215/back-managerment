package io.snow.springcloud.userservice.service;

import io.snow.springcloud.userservice.entitys.Permission;
import io.snow.springcloud.userservice.service.dto.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMenuService {
    Page<PermissionDTO> getAllMenus(Pageable pageable);

    List<PermissionDTO> getDefaultMenu();
}
