package io.snow.model.vo;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public class UserVo extends BaseVo implements Serializable {

    private Long id;

    private String userName;

    private String password;

    private String emile;

    private String gender;

    private String phoneNumber;

    private String profile;

    private List<RoleVo> authorities;

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

    public void setPassword(String password) {
        this.password = password;
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

    public Collection<RoleVo> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return userName;
    }

    public void setAuthorities(List<RoleVo> authorities) {
        this.authorities = authorities;
    }
}
