package io.snow.springcloud.userservice.controller.outrest;


import io.snow.model.vo.OrgVo;
import io.snow.rest.common.ResponseData;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.service.IOrgManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/orgManager")
public class OrgManagerController {

    private static final Logger logger = LoggerFactory.getLogger(OrgManagerController.class);

    @Autowired
    private IOrgManagerService orgManagerService;

    @PostMapping("/edit")
    public ResponseData editApiEntity(@RequestBody OrgVo orgVo, @RequestHeader("userName") String userName){
        try {
            orgVo.setLastModifiedBy(userName);
            orgVo.setLastModifiedDate(Instant.now());

            int row = orgManagerService.editOgrInfo(orgVo);
            if (row > 0){
                return ResponseData.ok(row);
            }else {
                return ResponseData.error("更新组织信息失败");
            }
        } catch (Exception e) {
            logger.error("edit org vo error:{0}",e);
            return ResponseData.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseData createApiEntity(@RequestBody OrgVo orgVo, @RequestHeader("userName") String userName){
        try {
            orgVo.setCreatedBy(userName);
            orgVo.setCreatedDate(Instant.now());
            OrgVo orgByOrgId = orgManagerService.getOrgByOrgId(orgVo.getOrgId());
            if (orgByOrgId!=null){
                return ResponseData.error("已经存在orgId =" + orgByOrgId.getOrgId() + ";的组织");
            }
            int row = orgManagerService.createOrg(orgVo);
            if (row > 0){
                return ResponseData.ok(row);
            }else {
                return ResponseData.error("创建组织失败");
            }
        } catch (Exception e) {
            logger.error("create org error:{0}",e);
            return ResponseData.error(e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResponseData queryOrg(@RequestBody OrgVo orgVo){
        logger.info("query org : {}",orgVo);
        try {
            List<OrgVo> result =  orgManagerService.queryOrgByName(orgVo.getOrgName());
            return ResponseData.ok(result);
        } catch (Exception e) {
            logger.error("query orgVo exception : {0}",e);
            return ResponseData.error("查询组织失败");
        }
    }


    @GetMapping("/list")
    public ResponseData listApi(@RequestParam("pageNum") Integer pageIndex,@RequestParam("pageSize") Integer pageSize){
        try {
            PageResult result = orgManagerService.listOrg(pageIndex, pageSize);
            return ResponseData.ok(result);
        } catch (Exception e) {
            logger.error("query:{0}",e);
            return ResponseData.error("查询失败");
        }
    }
}
