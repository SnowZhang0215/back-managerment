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
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service("mobileUserDetailService")
public class MobileUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(MobileUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("load user by mobile");
        return null;
    }
}
