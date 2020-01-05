package io.snow.springcloud.auth.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class DBUserDetails implements UserDetails {

    private String userName;

    private String password;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    public DBUserDetails(LinkedHashMap<String,Object> map) {
        this.userName = String.valueOf(map.get("userName"));
        this.password = String.valueOf(map.get("password"));
        if (null != map.get("authorities")){
            List<Object> authoritiesMap = (List<Object>) map.get("authorities");
            for (int i = 0; i < authoritiesMap.size(); i++) {
                LinkedHashMap item = (LinkedHashMap) authoritiesMap.get(i);
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority((String) item.get("code"));
                authorities.add(simpleGrantedAuthority);
                if (item.get("roleApis")!=null){
                    List<Map<String,Object>> permissions = (List<Map<String, Object>>) map.get("roleApis");
                    if (permissions!=null){
                        for (Map<String, Object> subItem : permissions) {
                            GrantedAuthority authority = new SimpleGrantedAuthority((String) subItem.get("path"));
                            authorities.add(authority);
                        }
                    }
                }
            }
        }
        authorities.add(new SimpleGrantedAuthority("/user-service/api/account/info"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
