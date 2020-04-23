package io.snow.springcloud.userservice.mapper;


import io.snow.model.vo.RoleVo;
import io.snow.model.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper {

    UserVo findUserByUserName(String userName);

    int insertUser(UserVo vo);

    void saveUserAndRole(Map map);

    //列表展示用户。不级联权限等。
    List<RoleVo> listUserPageable();

    //获取用户的详情。需要获取用户的角色，不需要获取权限
    UserVo getUserDetail(Long uId);

    int updateUser(UserVo userVo);

    int deleteUserRole(Long userId);

    int deleteUsers(List<Long> deleteIds);

    List<UserVo> selectUserByUserName(String userName);
}
