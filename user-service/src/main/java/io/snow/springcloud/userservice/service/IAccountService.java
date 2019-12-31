package io.snow.springcloud.userservice.service;

import io.snow.springcloud.userservice.entitys.User;

import java.util.Optional;

public interface IAccountService {
    Optional<User> getUserWithAuthorities();
}
