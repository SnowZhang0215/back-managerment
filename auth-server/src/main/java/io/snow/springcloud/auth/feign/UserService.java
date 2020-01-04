package io.snow.springcloud.auth.feign;

import io.snow.rest.common.ResponseData;
import io.snow.springcloud.auth.feign.impl.CallUserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "user-service",fallback = CallUserServiceImpl.class)
@Component
public interface UserService {
    @PostMapping("/service/user/findUserByUserName")
    ResponseData findUserByUserName(@RequestBody String userName);
}
