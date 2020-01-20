package io.snow.springcloud.userservice.mapper;


import io.snow.model.vo.UserVo;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    UserVo findUserByUserName(String userName);


    UserVo saveUserAndRole(UserVo newUser);
}
