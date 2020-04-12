package io.snow.springcloud.userservice.controller.outrest;

import io.snow.model.vo.RoleVo;
import io.snow.rest.common.ResponseData;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @GetMapping("/manager/list")
    public ResponseData getAllRoles(@RequestParam("pageNum") Integer pageIndex,@RequestParam("pageSize") Integer pageSize){
        logger.info("load all roles page : {},{}",pageIndex,pageSize);
        try {
            PageResult pageRoles = roleService.getAllRoles(pageIndex, pageSize);
            return ResponseData.ok(pageRoles);
        } catch (Exception e) {
            logger.error("get all roles exception : {0}",e);
           return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("/manager/getAllRole")
    public ResponseData getAllRoleForUser(){
        logger.info("load all roles ");
        try {
            List<RoleVo> all = roleService.getAll();
            return ResponseData.ok(all);
        } catch (Exception e) {
            logger.error("get all roles exception : {0}",e);
            return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("/manager/getrolepermission/{id}")
    public ResponseData getRoleAllPermission(@PathVariable("id") Long rId){
        logger.info("load role permission");
        try {
            List<Long> rolePermissionIds = roleService.getRolePermission(rId);
            return ResponseData.ok(rolePermissionIds);
        } catch (Exception e) {
            logger.error("get role permission exception : {0}",e);
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 为角色授权
     * @param map 请求参数。
     * @return
     */
    @PostMapping("/manager/authrole")
    public ResponseData authRole(@RequestBody Map<String,Object> map){
        logger.info("auth role: {}" , map);
        try {
            Map<String, Object> result = roleService.authRolePermission(map);
            return ResponseData.ok(result);
        } catch (Exception e) {
            logger.error("auth role permission : {0}",e);
            return ResponseData.error("角色授权失败");
        }
    }

    @PostMapping("/manager/add")
    public ResponseData addRole(@RequestBody RoleVo roleVo,@RequestHeader("userName")String userName){
        logger.info("create role");
        try {
            roleVo.setCreatedBy(userName);
            roleVo.setCreatedDate(Instant.now());
            int row =  roleService.createRole(roleVo);
            return ResponseData.ok(row);
        } catch (Exception e) {
            logger.error("create permission : {0}",e);
            return ResponseData.error("新建权限失败");
        }
    }
    @PostMapping("/manager/delete")
    public ResponseData deleteRoles(@RequestBody List<RoleVo> roles){
        logger.info("delete roles:{}" , roles);
        try {
           if (!roles.isEmpty()){
               List<Long> deleteIds = new ArrayList<>();
               for (RoleVo r: roles){
                   deleteIds.add(r.getId());
               }
              int row = roleService.deleteRoles(deleteIds);

               return ResponseData.ok(row);

           }else {
               return ResponseData.error("选择数据为空");
           }
        } catch (Exception e) {
            logger.error("delete role : {0}",e);
            return ResponseData.error("删除数据失败");
        }
    }

    @GetMapping("/manager/detail/{id}")
    public ResponseData getRoleDetail(@PathVariable("id") Long id){
        logger.info("get role detail:{}",id);
        try {
            RoleVo roleVo = roleService.getRoleById(id);
            return ResponseData.ok(roleVo);
        }catch (Exception e){
            logger.error("get role detail exception: {0}",e);
            return ResponseData.error("获取详情失败");
        }
    }
}
