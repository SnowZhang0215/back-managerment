//package io.snow.springcloud.apigateway.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
//@Configuration
//@EnableResourceServer
//public class SecurityConfig extends ResourceServerConfigurerAdapter {
//
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//
//    private static final String[] AUTH_WHITELIST = {
//            "/auth-server/**",
//            "/user-service/menu/getUserMenus/**",//用户菜单
//            "/user-service/account/register" //注册
//
//    };
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.cors().disable();
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
//                .authorizeRequests();
//        for (String au:AUTH_WHITELIST) {
//            http.authorizeRequests().antMatchers(au).permitAll();
//        }
//        http.authorizeRequests().anyRequest().authenticated();
//        logger.info("registry : {}" ,registry);
//        registry.anyRequest()
//                .access("@permissionService.hasPermission(request,authentication)");
//    }
//}
