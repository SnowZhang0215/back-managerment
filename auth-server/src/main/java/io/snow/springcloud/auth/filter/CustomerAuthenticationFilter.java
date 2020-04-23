package io.snow.springcloud.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class CustomerAuthenticationFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomerAuthenticationFilter.class);

    private static final String AUTH_TYPE = "auth_type";

    private static final String OAUTH_TOKEN_URL = "/oauth/token";

    private RequestMatcher requestMatcher;

    /**
     * 自定义拦截，拦截请求。
     * @param applicationContext
     */
    public CustomerAuthenticationFilter(ApplicationContext applicationContext) {
       this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(OAUTH_TOKEN_URL,"GET"),
               new AntPathRequestMatcher(OAUTH_TOKEN_URL,"POST"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (requestMatcher.matches((HttpServletRequest) request)){
            logger.info("request:{}",request);
            chain.doFilter(request,response);
        }else {
            chain.doFilter(request,response);
        }
    }
}
