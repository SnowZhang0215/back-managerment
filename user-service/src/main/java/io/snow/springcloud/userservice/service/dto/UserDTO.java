package io.snow.springcloud.userservice.service.dto;

import io.snow.springcloud.userservice.entitys.Permission;
import io.snow.springcloud.userservice.entitys.Role;
import io.snow.springcloud.userservice.entitys.User;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    private String userName;

    private List<String> authorities;

    private String emile;

    private ZonedDateTime createTime;

    private String gender;

    private String phoneNumber;

    private String profile;

    private List<PermissionDTO> userMenus = new ArrayList<>();

    private Set<Long> userPermission = new HashSet<>();

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getEmile() {
        return emile;
    }

    public void setEmile(String emile) {
        this.emile = emile;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public UserDTO(User user) {

        this.id = user.getId();
        this.userName = user.getUserName();
        this.emile = user.getEmile();
        this.createTime = user.getCreateTime();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
        this.profile = user.getProfile();
        this.authorities = user.getAuthorities().stream()
            .map(Role::getCode)
            .collect(Collectors.toList());

        List<List<Permission>> collect = user.getAuthorities().stream().map(Role::getRolePermissions).collect(Collectors.toList());



        if (!collect.isEmpty()){
            for(List<Permission> list : collect){
                for (Permission permission : list){
                    userPermission.add(permission.getId());
                    PermissionDTO permissionDTO = new PermissionDTO(permission);
                    if (permissionDTO.getParentId() == null){
                        //父菜单
                        this.userMenus.add(permissionDTO);
                    }
                }
            }
            removeNotAllowChildren(userMenus,userPermission);
        }
    }

    /**
     * 处理用户菜单中子菜单用户不能访问的
     * @param userMenus
     * @param userPermission
     */
    private void removeNotAllowChildren(List<PermissionDTO> userMenus, Set<Long> userPermission) {
        Iterator<PermissionDTO> iterator = userMenus.iterator();
        while (iterator.hasNext()){
            PermissionDTO permissionDTO = iterator.next();
            if (!permissionDTO.getChildren().isEmpty()){
                removeNotAllowChildren(permissionDTO.getChildren(),userPermission);
            }else {
                if (!userPermission.contains(permissionDTO.getId())){
                    //用户权限中没有的菜单，需要移除。
                    iterator.remove();
                }
            }
        }
    }

    public List<PermissionDTO> getUserMenus() {
        return userMenus;
    }

    public void setUserMenus(List<PermissionDTO> userMenus) {
        this.userMenus = userMenus;
    }
}
