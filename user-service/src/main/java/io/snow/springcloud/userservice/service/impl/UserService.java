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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("managerUserService")
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public int createUser(UserVo userVo) throws Exception {
        userVo.setPassword(bCryptPasswordEncoder.encode(userVo.getUserName()));
        UserVo user = userMapper.findUserByUserName(userVo.getUserName());
        if (user != null){
            throw new Exception("user exist");
        }
        userMapper.insertUser(userVo);
        if (userVo.getUserHasRoleId()!=null){
            Map<String,Object> userRole = new HashMap<>();
            userRole.put("user",userVo);
            userRole.put("role",userVo.getUserHasRoleId());
            userMapper.saveUserAndRole(userRole);
        }else {
            throw new Exception("用户角色不可为空");
        }
        return 0;
    }

    @Override
    public int deleteUsers(List<Long> deleteIds) {
        if (deleteIds.size()>0){
            for (Long id : deleteIds){
                userMapper.deleteUserRole(id);
            }
        }
        return userMapper.deleteUsers(deleteIds);
    }

    @Override
    public int updateUser(UserVo userVo) {
        userMapper.updateUser(userVo);
        if (userVo.getUserHasRoleId()!=null){
            userMapper.deleteUserRole(userVo.getId());
            Map<String,Object> userRole = new HashMap<>();
            userRole.put("user",userVo);
            userRole.put("role",userVo.getUserHasRoleId());
            userMapper.saveUserAndRole(userRole);
        }else {
            userMapper.deleteUserRole(userVo.getId());
        }
        return 0;
    }
}
