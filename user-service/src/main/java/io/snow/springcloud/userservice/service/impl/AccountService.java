package io.snow.springcloud.userservice.service.impl;

import io.snow.springcloud.userservice.dao.UserRepository;
import io.snow.springcloud.userservice.entitys.User;
import io.snow.springcloud.userservice.security.SecurityUtils;
import io.snow.springcloud.userservice.service.IAccountService;
import io.snow.springcloud.userservice.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountService implements IAccountService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userName -> userRepository.findOneWithAuthoritiesByUserName(userName));
    }
}
