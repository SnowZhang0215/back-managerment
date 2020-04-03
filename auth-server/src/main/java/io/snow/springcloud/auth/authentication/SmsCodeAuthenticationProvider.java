package io.snow.springcloud.auth.authentication;

import io.snow.springcloud.auth.service.SmsVerificationCodeService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

/**
 * 短信验证码验证的provider
 * provider与AuthenticationManager 和 userDetailsService之间的关系
 * manager是顶级接口，通过provider调用 userDetails的loadUserByName方法加载用户，并认证。
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    //短信验证码相关服务。
    private SmsVerificationCodeService smsVerificationCodeService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null){
            throw new UsernameNotFoundException("手机号不存在");
        }
        String verificationCode = smsVerificationCodeService.getVerificationCode((String) authenticationToken.getPrincipal());
        String userSubmitCode = (String) authenticationToken.getCredentials();
        if(StringUtils.isEmpty(verificationCode)){
            throw new BadCredentialsException("验证码已经过期");
        }
        if(verificationCode.equalsIgnoreCase(userSubmitCode)){
            SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(user,user.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        }else {
            throw new BadCredentialsException("验证码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setSmsVerificationCodeService(SmsVerificationCodeService smsVerificationCodeService) {
        this.smsVerificationCodeService = smsVerificationCodeService;
    }
}
