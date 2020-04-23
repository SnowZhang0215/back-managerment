package io.snow.springcloud.userservice.service.impl;

import io.snow.model.vo.RoleVo;
import io.snow.model.vo.UserVo;
import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.mapper.RoleMapper;
import io.snow.springcloud.userservice.mapper.UserMapper;
import io.snow.springcloud.userservice.security.AuthoritiesConstants;
import io.snow.springcloud.userservice.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccountService implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserVo getUserWithAuthorities(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public ResponseData createUser(Map<String, String> map) throws Exception {
        String userName = map.get("userName").toLowerCase();
        UserVo userVo = userMapper.findUserByUserName(userName);
        if (userVo != null){
            return ResponseData.error("user exist");
        }
        String password = map.get("password");
        UserVo newUser = new UserVo();
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        newUser.setUserName(userName);
        newUser.setPassword(encryptedPassword);
        userMapper.insertUser(newUser);
        RoleVo roles = roleMapper.findRoleByRoleName(AuthoritiesConstants.USER);
        Map<String,Object> userRole = new HashMap<>();
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(roles.getId());
        userRole.put("user",newUser);
        userRole.put("role",roleIds);
        userMapper.saveUserAndRole(userRole);
        logger.info("created user : {}",newUser);
        return ResponseData.ok("注册成功");
    }

    @Override
    public ResponseData loginAccount(Map<String, String> map) {
        return null;
    }
}
