package io.snow.springcloud.userservice.controller.outrest;

import io.snow.model.vo.RoleVo;
import io.snow.model.vo.UserVo;
import io.snow.rest.common.ResponseData;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.service.IUserService;
import io.snow.springcloud.userservice.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("managerUserService")
    private IUserService userService;

    @GetMapping("/manager/list")
    public ResponseData getAllUsers(@RequestParam("pageNum") Integer pageIndex,@RequestParam("pageSize") Integer pageSize){
        logger.info("load all user");
        try {
            PageResult pageRoles = userService.listUserPage(pageIndex, pageSize);
            return ResponseData.ok(pageRoles);
        } catch (Exception e) {
            logger.error("get all user exception : {0}",e);
           return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("/manager/detail/{id}")
    public ResponseData getUserDetail(@PathVariable("id") Long uId){
        logger.info("load user detail: {}",uId);
        try {
            UserVo userVo = userService.getUserDetail(uId);
            return ResponseData.ok(userVo);
        } catch (Exception e) {
            logger.error("get user detail exception : {0}",e);
            return ResponseData.error(e.getMessage());
        }
    }


    @PostMapping("/manager/add")
    public ResponseData addUser(@RequestBody UserVo userVo,@RequestHeader("userName")String userName){
        logger.info("create user : {}",userVo);
        try {
            userVo.setCreatedBy(userName);
            userVo.setCreatedDate(Instant.now());
            int row =  userService.createUser(userVo);
            return ResponseData.ok(row);
        } catch (Exception e) {
            logger.error("create user exception : {0}",e);
            return ResponseData.error("新建用户失败");
        }
    }

    @PostMapping("/manager/edit")
    public ResponseData editUser(@RequestBody UserVo userVo,@RequestHeader("userName")String userName){
        logger.info("create user : {}",userVo);
        try {
            userVo.setLastModifiedBy(userName);
            userVo.setLastModifiedDate(Instant.now());
            int row =  userService.updateUser(userVo);
            return ResponseData.ok(row);
        } catch (Exception e) {
            logger.error("create user exception : {0}",e);
            return ResponseData.error("修改用户失败");
        }
    }

    @PostMapping("/manager/delete")
    public ResponseData deleteUsers(@RequestBody List<UserVo> users){
        logger.info("delete users:{}" , users);
        try {
           if (!users.isEmpty()){
               List<Long> deleteIds = new ArrayList<>();
               for (UserVo r: users){
                   deleteIds.add(r.getId());
               }
               int row = userService.deleteUsers(deleteIds);
               return ResponseData.ok(row);
           }else {
               return ResponseData.error("选择数据为空");
           }
        } catch (Exception e) {
            logger.error("delete user : {0}",e);
            return ResponseData.error("删除数据失败");
        }
    }
}
