package io.snow.springcloud.userservice.controller.outrest;

import io.snow.model.vo.Permission;
import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.service.dto.PermissionDTO;
import io.snow.springcloud.userservice.service.impl.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            logger.error("get all menu exception : {}",e);
           return ResponseData.error(e.getMessage());
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
