package io.snow.springcloud.userservice.service.impl;

import io.snow.springcloud.userservice.dao.PermissionRepository;
import io.snow.springcloud.userservice.entitys.Permission;
import io.snow.springcloud.userservice.service.IMenuService;
import io.snow.springcloud.userservice.service.dto.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<PermissionDTO> getAllMenus(Pageable pageable) {

        return permissionRepository.findAll(pageable).map(permission -> new PermissionDTO(permission));
    }

    @Override
    public List<PermissionDTO> getDefaultMenu() {
        return permissionRepository.findAllByDefaultType().stream().map(PermissionDTO::new).collect(Collectors.toList());
    }
}
