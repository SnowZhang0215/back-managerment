package io.snow.springcloud.userservice.config;

import io.snow.springcloud.userservice.handler.AuthenticationExceptionEntryPoint;
import io.snow.springcloud.userservice.handler.CustmorAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint;

    @Autowired
    private CustmorAccessDeniedHandler custmorAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/account/login","/account/register")
                .permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore)
                .resourceId("user-service")
                .authenticationEntryPoint(authenticationExceptionEntryPoint)
                .accessDeniedHandler(custmorAccessDeniedHandler);
    }
}
