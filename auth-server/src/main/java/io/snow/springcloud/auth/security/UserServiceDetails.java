package io.snow.springcloud.auth.security;

import io.snow.rest.common.ResponseData;
import io.snow.springcloud.auth.feign.UserService;
import io.snow.springcloud.auth.security.userdetails.DBUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author snowzhang
 * user details
 * load user from db;
 */
@Service
public class UserServiceDetails implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceDetails.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("call user-service to load user form {}" , userName);
        ResponseData responseData = userService.findUserByUserName(userName);
        if (responseData.getErrorCode()!=200){
            throw new UsernameNotFoundException("user" + userName + responseData.getErrorMsg());
        }
        LinkedHashMap data = (LinkedHashMap) responseData.getData();
        DBUserDetails dbUserDetails = new DBUserDetails(data);
        logger.info("loaded user : {}",dbUserDetails);
        return  dbUserDetails;
    }
}
