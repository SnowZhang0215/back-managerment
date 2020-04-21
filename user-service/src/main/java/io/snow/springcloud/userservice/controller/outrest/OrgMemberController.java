package io.snow.springcloud.userservice.controller.outrest;


import io.snow.model.vo.OrgMemberVo;
import io.snow.rest.common.ResponseData;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.service.IOrgMemberService;
import io.snow.springcloud.userservice.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/orgMember")
public class OrgMemberController {

    private static final Logger logger = LoggerFactory.getLogger(OrgMemberController.class);

    @Autowired
    private IOrgMemberService orgMemberService;

    @Autowired
    @Qualifier("managerUserService")
    private IUserService userService;

    @PostMapping("/edit")
    public ResponseData editOrgMember(@RequestBody OrgMemberVo orgMemberVo, @RequestHeader("userName") String userName){
        try {
            orgMemberVo.setLastModifiedBy(userName);
            orgMemberVo.setLastModifiedDate(Instant.now());
            int row = orgMemberService.updateOrgMember(orgMemberVo);
            if (orgMemberVo.getOrgRoleType() == 0 || orgMemberVo.getOrgRoleType() ==1){
                //
            }
            return ResponseData.ok(row);
        } catch (Exception e) {
            logger.error("edit org member vo error:{0}",e);
            return ResponseData.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseData createOrgMember(@RequestBody OrgMemberVo orgMemberVo, @RequestHeader("userName") String userName,@RequestHeader("isAdmin") Integer isAdmin){
        try {
            orgMemberVo.setCreatedBy(userName);
            orgMemberVo.setCreatedDate(Instant.now());
            OrgMemberVo orgMemberByGameId = orgMemberService.getOrgMemberByGameId(orgMemberVo);
            if (isAdmin != 1){
                //不是管理员
                OrgMemberVo orgMemberVo1 = new OrgMemberVo();
                orgMemberVo1.setGameId(userName);
                OrgMemberVo requestUser = orgMemberService.getOrgMemberByGameId(orgMemberVo1);
                if (requestUser!=null && requestUser.getOrgId()!=null){
                    orgMemberVo.setOrgId(requestUser.getOrgId());
                }
            }

            if (orgMemberByGameId != null){
                if (orgMemberByGameId.getOrgId()!=null && !orgMemberByGameId.getOrgId().equals(orgMemberVo.getOrgId())){
                    return ResponseData.error("成员已经加入其它组织");
                }else {
                    orgMemberService.deleteOrgMember(orgMemberByGameId.getId());
                }
            }
            OrgMemberVo orgByName = orgMemberService.getOrgMemberByOrgIdAndMemberCode(orgMemberVo);
            if (orgByName!=null){
                return ResponseData.error("该成员已经在组织");
            }
            int row = orgMemberService.createOrgMember(orgMemberVo);
            if (row > 0){
                return ResponseData.ok(row);
            }else {
                return ResponseData.error("创建组织成员失败");
            }
        } catch (Exception e) {
            logger.error("create org member error:{0}",e);
            return ResponseData.error(e.getMessage());
        }
    }


    @GetMapping("/list")
    public ResponseData listOrgMember(@RequestParam("pageNum") Integer pageIndex,@RequestParam("pageSize") Integer pageSize,@RequestHeader("isAdmin") Integer isAdmin,@RequestHeader("userName") String userName){
        try {
            PageResult result = orgMemberService.listOrgMember(pageIndex, pageSize,isAdmin,userName);
            return ResponseData.ok(result);
        } catch (Exception e) {
            logger.error("query:{0}",e);
            return ResponseData.error("查询组织成员列表失败");
        }
    }
}
