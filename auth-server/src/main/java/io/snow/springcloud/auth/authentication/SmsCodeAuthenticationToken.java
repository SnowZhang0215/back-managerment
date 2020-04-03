package io.snow.springcloud.auth.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 交个 AuthenticationManager去认证的Token对象。
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object credentials;

    public SmsCodeAuthenticationToken(String mobile,String code){
        super(null);
        this.principal = mobile;
        this.credentials = code;
        super.setAuthenticated(false);//初始是未认证状态
    }

    /**
     * 认证成功之后的对象封装。
     * @param userDetails 认证成功后的user
     * @param authorities 认证成功后user具有的权限
     */
    public SmsCodeAuthenticationToken(UserDetails userDetails,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userDetails;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
