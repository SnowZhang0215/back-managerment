package io.snow.springcloud.gateway.config;

import io.snow.springcloud.gateway.handler.AuthenticationExceptionEntryPoint;
import io.snow.springcloud.gateway.handler.CustomerAccessDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.request.RequestContextListener;

/**
 * SecurityConfig
 */
@Configuration
@EnableResourceServer
@EnableOAuth2Client
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Autowired
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    @Autowired
    private AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint;
//
//    @Autowired
//    private TokenStore tokenStore;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private static final String[] AUTH_WHITELIST = {
            "/auth-service/**",
            "/user-service/menu/default/**",
            "/**/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "swagger-resources/configuration/ui",
            "/doc.html",
            "/webjars/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.authorizeRequests().antMatchers("/v2/api-docs").permitAll();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        for (String au:AUTH_WHITELIST) {
            http.authorizeRequests().antMatchers(au).permitAll();
        }
        http.authorizeRequests().anyRequest().authenticated();
        logger.info("registry : {}" ,registry);
        registry.anyRequest()
                .access("@permissionService.hasPermission(request,authentication)");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
//                .tokenStore(tokenStore)
//                .resourceId("user-service")
                .expressionHandler(expressionHandler)
                .authenticationEntryPoint(authenticationExceptionEntryPoint)
                .accessDeniedHandler(customerAccessDeniedHandler)
        ;
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    /**
     * add RequestContextListener
     * 如果没有此Bean 资源服务通过token向auth-server获取用户信息时会报错：
     * Error creating bean with name 'scopedTarget.oauth2ClientContext':
     * Scope 'session' is not active for the current thread; consider defining a scoped proxy
     * for this bean if you intend to refer to it from a singleton; nested exception is java.lang.IllegalStateException:
     * No thread-bound request found: Are you referring to request attributes outside of an actual web request,
     * or processing a request outside of the originally receiving thread?
     * If you are actually operating within a web request and still receive this message,
     * your code is probably running outside of DispatcherServlet/DispatcherPortlet:
     * In this case, use RequestContextListener or RequestContextFilter to expose the current request.
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
