package io.snow.springcloud.userservice.service.dto;

import io.snow.model.vo.RoleVo;
import io.snow.model.vo.UserVo;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    private String userName;

    private List<String> authorities;

    private String emile;

    private String gender;

    private String phoneNumber;

    private String profile;

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

    public UserDTO(UserVo user) {

        this.id = user.getId();
        this.userName = user.getUserName();
        this.emile = user.getEmile();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
        this.profile = user.getProfile();
        this.authorities = user.getAuthorities().stream()
            .map(RoleVo::getCode)
            .collect(Collectors.toList());
    }
}
