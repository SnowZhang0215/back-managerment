package io.snow.springcloud.userservice.controller.outrest;

import io.snow.model.vo.Permission;
import io.snow.rest.common.ResponseData;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.service.impl.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @GetMapping("/manage/list")
    public ResponseData getAllParentMenu(){
        logger.info("getAllMenu");
        try {
            List<Permission> allMenus = menuService.getAllParentMenus();
            return ResponseData.ok(allMenus);
        } catch (Exception e) {
            logger.error("get all menu exception : {0}",e);
           return ResponseData.error(e.getMessage());
        }
    }
    @PostMapping("/manage/subMenu")
    public ResponseData getSubMenuByParentId(@RequestBody Map<String,Object> map){
        logger.info("get subMenu by ParentId :{}",map);
        try {
            PageResult result = menuService.getSubMenuByParentId(map);
            return ResponseData.ok(result);
        } catch (Exception e) {
            logger.error("get subMenu by ParentId : {0}",e);
            return ResponseData.error(e.getMessage());
        }
    }

    @PostMapping("/manage/add")
    public ResponseData addPermission(@RequestBody Permission permission,@RequestHeader("userName")String userName){
        logger.info("create permission");
        try {
            permission.setCreatedBy(userName);
            permission.setCreatedDate(Instant.now());
            int row =  menuService.createPermission(permission);
            return ResponseData.ok(row);
        } catch (Exception e) {
            logger.error("create permission : {0}",e);
            return ResponseData.error("新建权限失败");
        }
    }

    @GetMapping("/manage/detail/{id}")
    public ResponseData getPermissionDetail(@PathVariable("id") Long id,@RequestHeader("userName")String userName){
        logger.info("get permission detail:{}",id);
        try {
            Permission permission = menuService.getPermissionDetail(id);
            return ResponseData.ok(permission);
        }catch (Exception e){
            logger.error("get permission detail exception: {0}",e);
            return ResponseData.error("获取详情失败");
        }
    }


    @GetMapping("/getUserMenus")
    public ResponseData getDefaultMenu(@RequestHeader("userName") String userName){
        logger.info("get default menu : {}",userName);
        try {
            List<Permission> userNavMenus = menuService.getUserNavMenus(userName);
            return ResponseData.ok(userNavMenus);
        } catch (Exception e) {
            logger.error("get default menus error: {}",e);
            return ResponseData.error(e.getMessage());
        }
    }
}
