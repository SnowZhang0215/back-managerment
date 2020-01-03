package io.snow.springcloud.userservice.controller;

import io.snow.springcloud.userservice.controller.rest.ResponseCode;
import io.snow.springcloud.userservice.controller.rest.ResponseData;
import io.snow.springcloud.userservice.service.dto.UserDTO;
import io.snow.springcloud.userservice.service.impl.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping("/info")
    public ResponseData<?> getUserInfo() {
        logger.info("get user info");
        ResponseData<UserDTO> returnData = accountService.getUserWithAuthorities().map(user -> ResponseData.ok(new UserDTO(user))).orElse(
                ResponseData.error("user not login")
        );
        logger.info("return data : {}",returnData);
        return returnData;
    }

    @PostMapping("/register")
    public ResponseData<?> registerUser(@RequestBody Map<String,String> map) {
        logger.info("register user : {}",map);
        if (StringUtils.isEmpty(map.get("userName")) || StringUtils.isEmpty(map.get("password"))){
            return ResponseData.error(ResponseCode.ParamsError,"params error");
        }
        ResponseData responseData = accountService.createUser(map);
        logger.info("response data : {}",responseData);
        return responseData;
    }

    @PostMapping("/login")
    public ResponseData<?> login(@RequestBody Map<String,String> map) {
        logger.info("login params : {}",map);
        if (StringUtils.isEmpty(map.get("userName")) || StringUtils.isEmpty(map.get("password"))){
            return ResponseData.error(ResponseCode.ParamsError,"params error");
        }
        ResponseData responseData =  accountService.loginAccount(map);
        logger.info("response data : {}",responseData);
        return responseData;
    }
}
