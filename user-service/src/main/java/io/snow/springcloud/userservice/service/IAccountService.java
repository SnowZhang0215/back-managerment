package io.snow.springcloud.userservice.service;

import io.snow.model.vo.UserVo;
import io.snow.rest.common.ResponseData;

import java.util.Map;

public interface IAccountService {
    UserVo getUserWithAuthorities(String userName);

    ResponseData createUser(Map<String, String> map) throws Exception;

    ResponseData loginAccount(Map<String, String> map);
}
