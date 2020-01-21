package io.snow.springcloud.userservice.mapper;


import io.snow.model.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface UserMapper {

    UserVo findUserByUserName(String userName);

    int insertUser(UserVo vo);

    void saveUserAndRole(Map map);
}
