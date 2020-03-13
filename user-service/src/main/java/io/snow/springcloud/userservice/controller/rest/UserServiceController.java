package io.snow.springcloud.userservice.controller.rest;

import io.snow.model.vo.Permission;
import io.snow.model.vo.UserVo;
import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.mapper.PermissionMapper;
import io.snow.springcloud.userservice.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/service/user")
public class UserServiceController {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @PostMapping("/findUserByUserName")
    public ResponseData<UserVo> findUserByUserName(@RequestBody String userName) {
        logger.info("load user from db by oauth-service : {}", userName);
        UserVo userVo = userMapper.findUserByUserName(userName);
        if (userVo!=null){
            List<Permission> userPermission = getUserPermission(userVo.getUserName());
            logger.info("user permission:{}",userPermission);
            userVo.setUserPermissions(userPermission);
        }
        if (userVo == null) {
            return ResponseData.error(" user not found");
        } else {
            return ResponseData.ok(userVo);
        }
    }

    /**
     * 根据用户名获取用户的所有权限。用于认证server鉴权
     * @param userName username
     * @return a list of permissions;
     */
    private List<Permission> getUserPermission(String userName) {
        return permissionMapper.getUserPermissionWithApi(userName);
    }
}
