package io.snow.springcloud.userservice.service.dto;

import io.snow.model.vo.Permission;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionDTO {

    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String url;

    private String isMenu;

    private String level;

    private Long sort;

    private String status;

    private String icon;

    private String defaultType;

    private Long parentId;

    private List<PermissionDTO> children;

    private String component;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.code = permission.getCode();
        this.parentCode = permission.getParentCode();
        this.name = permission.getName();
        this.url = permission.getUrl();
        this.isMenu = permission.getIsMenu();
        this.level = permission.getLevel();
        this.sort = permission.getSort();
        this.status = permission.getStatus();
        this.icon = permission.getIcon();
        this.component = permission.getComponent();
        this.defaultType = permission.getDefaultType();
        this.children = permission.getChildren().stream().map(PermissionDTO::new).collect(Collectors.toList());
    }

    public List<PermissionDTO> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionDTO> children) {
        this.children = children;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
