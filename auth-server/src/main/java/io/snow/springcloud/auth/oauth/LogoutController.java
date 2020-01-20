package io.snow.springcloud.auth.oauth;

import io.snow.rest.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class LogoutController {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @PostMapping(value = "/logout")
    public @ResponseBody
    ResponseData revokeToken(@RequestHeader("Authorization") String access_token){
        ResponseData responseData = ResponseData.ok(null);
        if (!StringUtils.isEmpty(access_token)){
            access_token = access_token.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
            if (consumerTokenServices.revokeToken(access_token)){
                responseData = ResponseData.ok("注销成功");
            }else {
                responseData = ResponseData.error("注销失败");
            }
        }
        return responseData;
    }
}
