package io.snow.springcloud.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.snow.rest.common.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ErrorFilter extends ZuulFilter {
    Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        ResponseData<Object> error = ResponseData.error(500, throwable.getCause().getMessage());

        ctx.getResponse().setContentType("application/json");
        ctx.getResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(ctx.getResponse().getOutputStream(), error);
        } catch (IOException e) {
            log.error("outputStream: {}",e);
        }
        log.error("this is a ErrorFilter :" + throwable.getCause().getMessage(), throwable);
        return null;
    }
}
