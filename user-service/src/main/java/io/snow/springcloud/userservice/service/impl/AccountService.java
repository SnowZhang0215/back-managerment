package io.snow.springcloud.userservice.service.impl;

import io.snow.springcloud.userservice.UserService;
//import io.snow.springcloud.userservice.config.OAuthClientConfig;
import io.snow.springcloud.userservice.controller.rest.ResponseData;
import io.snow.springcloud.userservice.dao.RoleRepository;
import io.snow.springcloud.userservice.dao.UserRepository;
import io.snow.springcloud.userservice.entitys.Role;
import io.snow.springcloud.userservice.entitys.User;
import io.snow.springcloud.userservice.security.AuthoritiesConstants;
import io.snow.springcloud.userservice.security.SecurityUtils;
import io.snow.springcloud.userservice.service.IAccountService;
import io.snow.springcloud.userservice.service.dto.UserDTO;
import io.snow.springcloud.userservice.service.figen.AuthServerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Security;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AccountService implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthServerClient authServer;

//    @Autowired
//    private OAuthClientConfig oAuthClientConfig;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userName -> userRepository.findOneWithAuthoritiesByUserName(userName));
    }

    @Override
    public ResponseData createUser(Map<String, String> map) {
        String userName = map.get("userName").toLowerCase();
        String password = map.get("password");
        Optional<User> optional = userRepository.findUserByUserName(userName);
        if (optional.isPresent()){
            return ResponseData.error("user exist");
        }
        User user = insertUserToDB(userName,password);
        return ResponseData.ok(new UserDTO(user));
    }

    @Override
    public ResponseData loginAccount(Map<String, String> map) {
//        map.put("client_id",oAuthClientConfig.getId());
//        map.put("client_secret",oAuthClientConfig.getClientSecret());
//        map.put("grant_type",oAuthClientConfig.getGrantType());
//        map.put("scope",oAuthClientConfig.getScope());
        ResponseEntity entity = authServer.oauthLogin(map);
        logger.info("result : {}",entity);
        return null;
    }

    private User insertUserToDB(String userName, String password) {
        User newUser = new User();
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        newUser.setUserName(userName);
        newUser.setPassword(encryptedPassword);
        List<Role> roles = roleRepository.findByName(AuthoritiesConstants.USER);
        newUser.setAuthorities(roles);
        newUser = userRepository.save(newUser);
        logger.info("created user : {}",newUser);
        return newUser;
    }
}
