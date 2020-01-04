package io.snow.springcloud.userservice.controller.rest;

import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.dao.UserRepository;
import io.snow.springcloud.userservice.entitys.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/service/user")
public class UserServiceController {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/findUserByUserName")
    public ResponseData<User> findUserByUserName(@RequestBody String userName){
        logger.info("load user from db by oauth-service : {}",userName);
        Optional<User> optional = userRepository.findOneWithAuthoritiesByUserName(userName);
        return optional.map(user -> ResponseData.ok(user)).orElse(ResponseData.error("user not found"));
    }
}
