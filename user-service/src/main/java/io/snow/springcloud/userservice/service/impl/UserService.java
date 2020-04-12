package io.snow.springcloud.userservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.snow.model.vo.RoleVo;
import io.snow.model.vo.UserVo;
import io.snow.rest.common.page.PageRequest;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.mapper.UserMapper;
import io.snow.springcloud.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("managerUserService")
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult listUserPage(Integer pageIndex, Integer pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageIndex == null ? 1 : pageIndex);
        pageRequest.setPageSize(pageSize == null ? 10 : pageSize);
        Page<Object> page = PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), true);
        List<RoleVo> list = userMapper.listUserPageable();
        PageResult result = new PageResult();
        result.setContent(list);
        result.setTotalSize(page.getTotal());
        result.setPageNum(pageRequest.getPageNum());
        result.setPageSize(pageRequest.getPageSize());
        return result;
    }

    @Override
    public UserVo getUserDetail(Long uId) {
        return userMapper.getUserDetail(uId);
    }

    @Override
    public int createUser(UserVo userVo) {
        return 0;
    }

    @Override
    public int deleteUsers(List<Long> deleteIds) {
        return 0;
    }
}
