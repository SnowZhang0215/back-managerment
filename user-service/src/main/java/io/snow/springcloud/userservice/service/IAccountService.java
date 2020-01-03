package io.snow.springcloud.userservice.service;

import io.snow.springcloud.userservice.controller.rest.ResponseData;
import io.snow.springcloud.userservice.entitys.User;

import java.util.Map;
import java.util.Optional;

public interface IAccountService {
    Optional<User> getUserWithAuthorities();

    ResponseData createUser(Map<String, String> map);

    ResponseData loginAccount(Map<String, String> map);
}
