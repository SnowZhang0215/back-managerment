package io.snow.springcloud.auth.config;

import io.snow.springcloud.auth.authentication.SmsCodeAuthenticationProvider;
import io.snow.springcloud.auth.security.UserServiceDetails;
import io.snow.springcloud.auth.service.impl.SmsVerificationCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceDetails userDetailsService;

    @Autowired
    @Qualifier("mobileUserDetailService")
    private UserDetailsService mobileUserDetailService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
//                .authenticationEntryPoint(authenticationExceptionEntryPoint)
                .and()
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/**/oauth/**").permitAll()
                .anyRequest().authenticated()
        ;
    }

    @Bean
    public   BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setSmsVerificationCodeService(new SmsVerificationCodeServiceImpl());
        smsCodeAuthenticationProvider.setUserDetailsService(mobileUserDetailService);
        //将验证smscode的Provider纳入authenticationManager管理。
        auth.authenticationProvider(smsCodeAuthenticationProvider);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
