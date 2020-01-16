package io.snow.springcloud.userservice.controller.outrest;

import io.snow.rest.common.ResponseData;
import io.snow.springcloud.userservice.entitys.Permission;
import io.snow.springcloud.userservice.service.dto.PermissionDTO;
import io.snow.springcloud.userservice.service.impl.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseData getAllMenu(Pageable pageable){
        logger.info("getAllMenu : {}", pageable);
        try {
            Page<PermissionDTO> allMenus = menuService.getAllMenus(pageable);
            return ResponseData.ok(allMenus);
        } catch (Exception e) {
            logger.error("get all menu exception : {}",e);
           return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("/default/menus")
    public ResponseData getDefaultMenu(){
        logger.info("get default menu");
        try {
            List<PermissionDTO> defaultMenu = menuService.getDefaultMenu();
            return ResponseData.ok(defaultMenu);
        } catch (Exception e) {
            logger.error("get default menus error: {}",e);
            return ResponseData.error(e.getMessage());
        }
    }
}
