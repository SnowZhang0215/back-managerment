package io.snow.springcloud.userservice.service;

import io.snow.model.vo.UserVo;
import io.snow.rest.common.page.PageResult;

import java.util.List;

public interface IUserService {
    PageResult listUserPage(Integer pageIndex, Integer pageSize);

    UserVo getUserDetail(Long uId);

    int createUser(UserVo userVo) throws Exception;

    int deleteUsers(List<Long> deleteIds);

    int updateUser(UserVo userVo);
}
