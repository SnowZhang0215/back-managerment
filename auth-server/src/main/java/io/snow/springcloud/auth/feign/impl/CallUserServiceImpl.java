package io.snow.springcloud.auth.feign.impl;

import io.snow.rest.common.ResponseData;
import io.snow.springcloud.auth.feign.UserService;
import org.springframework.stereotype.Component;

@Component
public class CallUserServiceImpl implements UserService {
    @Override
    public ResponseData findUserByUserName(String userName) {
        return ResponseData.error(500,"call user-service error");
    }
}
