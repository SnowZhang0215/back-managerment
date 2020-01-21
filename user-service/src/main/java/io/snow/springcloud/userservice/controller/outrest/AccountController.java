package io.snow.springcloud.userservice.controller.outrest;

//import io.snow.rest.common.ResponseCode;
//import io.snow.rest.common.ResponseData;
//import io.snow.springcloud.userservice.service.dto.UserDTO;
//import io.snow.springcloud.userservice.service.impl.AccountService;
import io.snow.model.vo.UserVo;
import io.snow.rest.common.ResponseCode;
import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.service.dto.UserDTO;
import io.snow.springcloud.userservice.service.impl.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping("/info")
    public ResponseData<?> getUserInfo(@RequestHeader("userName") String userName) {
        logger.info("get user info");
        UserVo user = accountService.getUserWithAuthorities(userName);
        if (user == null){
            return ResponseData.error(" user not login");
        }else {
            return ResponseData.ok(new UserDTO(user));
        }
    }

    @PostMapping("/register")
    public ResponseData<?> registerUser(@RequestBody Map<String,String> map) {
        logger.info("register user : {}",map);
        if (StringUtils.isEmpty(map.get("userName")) || StringUtils.isEmpty(map.get("password"))){
            return ResponseData.error(ResponseCode.ParamsError,"params error");
        }
        ResponseData responseData = null;
        try {
            responseData = accountService.createUser(map);
        } catch (Exception e) {
            logger.error("register filed:{}",e);
            responseData = ResponseData.error("注册失败");
        }
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
