package io.snow.springcloud.userservice.service.figen;

//import io.snow.springcloud.userservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "auth-service")
public interface AuthServerClient {
    @RequestMapping(value = "oauth/token",method = RequestMethod.POST)
    ResponseEntity oauthLogin(@RequestBody Map<String,String> map);
}
