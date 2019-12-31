package io.snow.springcloud.userservice.controller;

import io.snow.springcloud.userservice.controller.rest.InternalServerErrorException;
import io.snow.springcloud.userservice.controller.rest.ResponseData;
import io.snow.springcloud.userservice.service.dto.UserDTO;
import io.snow.springcloud.userservice.service.impl.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/account")
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
    public ResponseEntity<?> registerUser(Map<String,String> map) {
        logger.info("register user : {}",map);

        return ResponseEntity.ok("register");
    }
}
