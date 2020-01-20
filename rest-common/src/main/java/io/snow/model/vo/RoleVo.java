package io.snow.model.vo;


import java.io.Serializable;
import java.util.List;

public class RoleVo extends BaseVo implements Serializable {

    private Long id;

    private String code;

    private String name;

    private List<Permission> rolePermissions;

    private List<ApiVo> roleApis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<Permission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public List<ApiVo> getRoleApis() {
        return roleApis;
    }

    public void setRoleApis(List<ApiVo> roleApis) {
        this.roleApis = roleApis;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
