package io.snow.springcloud.auth.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationExceptionEntryPoint implements AuthenticationEntryPoint {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationExceptionEntryPoint.class);
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.warn("auth failed from auth-server");
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"认证失败");
    }
}
