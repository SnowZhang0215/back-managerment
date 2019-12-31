package io.snow.springcloud.auth.security;

import io.snow.springcloud.auth.dao.UserRepository;
import io.snow.springcloud.auth.entitys.Role;
import io.snow.springcloud.auth.entitys.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("load user form db: {}" , userName);
        User dbUser = userRepository.findUserByUserName(userName);
        if (dbUser == null){
            throw new UsernameNotFoundException("user" + userName + " not exist!");
        }
        logger.info("loaded user : {}",dbUser);
        return dbUser;
    }
}
