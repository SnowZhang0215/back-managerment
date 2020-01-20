package io.snow.springcloud.userservice.service.impl;

import io.snow.model.vo.RoleVo;
import io.snow.model.vo.UserVo;
import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.mapper.RoleMapper;
import io.snow.springcloud.userservice.mapper.UserMapper;
import io.snow.springcloud.userservice.security.AuthoritiesConstants;
import io.snow.springcloud.userservice.service.IAccountService;
import io.snow.springcloud.userservice.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AccountService implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

//    @Autowired
//    private OAuthClientConfig oAuthClientConfig;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserVo getUserWithAuthorities(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    @Override
    public ResponseData createUser(Map<String, String> map) {
        String userName = map.get("userName").toLowerCase();
        String password = map.get("password");
        UserVo userVo = userMapper.findUserByUserName(userName);
        if (userVo != null){
            return ResponseData.error("user exist");
        }
        UserVo user = insertUserToDB(userName,password);
        return ResponseData.ok(new UserDTO(user));
    }

    @Override
    public ResponseData loginAccount(Map<String, String> map) {
        return null;
    }

    private UserVo insertUserToDB(String userName, String password) {
        UserVo newUser = new UserVo();
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        newUser.setUserName(userName);
        newUser.setPassword(encryptedPassword);
        RoleVo roles = roleMapper.findRoleByRoleName(AuthoritiesConstants.USER);
        newUser = userMapper.saveUserAndRole(newUser);
        logger.info("created user : {}",newUser);
        return newUser;
    }
}
