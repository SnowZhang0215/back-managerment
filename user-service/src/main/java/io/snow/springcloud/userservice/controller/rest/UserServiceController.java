package io.snow.springcloud.userservice.controller.rest;

import io.snow.model.vo.UserVo;
import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/service/user")
public class UserServiceController {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/findUserByUserName")
    public ResponseData<UserVo> findUserByUserName(@RequestBody String userName){
        logger.info("load user from db by oauth-service : {}",userName);
        UserVo userByUserName = userMapper.findUserByUserName(userName);
        if (userByUserName == null){
            return ResponseData.error(" user not found");
        }else {
            return ResponseData.ok(userByUserName);
        }
    }
}
