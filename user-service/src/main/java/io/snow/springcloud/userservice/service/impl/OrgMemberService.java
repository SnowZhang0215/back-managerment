package io.snow.springcloud.userservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.snow.model.vo.FightChangeVo;
import io.snow.model.vo.OrgMemberVo;
import io.snow.model.vo.RoleVo;
import io.snow.model.vo.UserVo;
import io.snow.rest.common.page.PageRequest;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.mapper.OrgMemberMapper;
import io.snow.springcloud.userservice.mapper.RoleMapper;
import io.snow.springcloud.userservice.mapper.UserMapper;
import io.snow.springcloud.userservice.service.IFightChangeService;
import io.snow.springcloud.userservice.service.IOrgMemberService;
import io.snow.springcloud.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrgMemberService implements IOrgMemberService {

    @Autowired
    private OrgMemberMapper orgMemberMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    @Qualifier("managerUserService")
    private IUserService userService;

    @Autowired
    private IFightChangeService fightChangeService;


    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public int updateOrgMember(OrgMemberVo orgMemberVo) throws Exception {
        String gameId = orgMemberVo.getGameId();
        UserVo userVo = userMapper.findUserByUserName(gameId);
        if (userVo == null){
            return createOrgMember(orgMemberVo);
        }
        RoleVo roleOrgMaster = roleMapper.findRoleByRoleName("ROLE_ORG_MASTER");
        RoleVo roleUser = roleMapper.findRoleByRoleName("ROLE_ORG_USER");
        ArrayList<Long> roleIds = new ArrayList<>();
        if (orgMemberVo.getOrgRoleType() == 0 || orgMemberVo.getOrgRoleType() == 1){
            roleIds.add(roleOrgMaster.getId());
            userVo.setUserHasRoleId(roleIds);
        }else {
            roleIds.add(roleUser.getId());
        }
        userVo.setUserHasRoleId(roleIds);
        userService.updateUser(userVo);
        OrgMemberVo saved = orgMemberMapper.selectByGameId(gameId);
        if(!saved.getFightCapacity().equals(orgMemberVo.getFightCapacity())){
            fightChangeService.insertFightChange(orgMemberVo);
        }
        return orgMemberMapper.updateOrgMemberVo(orgMemberVo);
    }

    @Override
    public OrgMemberVo getOrgMemberByOrgIdAndMemberCode(OrgMemberVo orgMemberVo) {
        if (orgMemberVo.getOrgId() == null || orgMemberVo.getGameId() == null){
            return null;
        }
        return orgMemberMapper.findOrgMemberByOrgIdAndGameId(orgMemberVo);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public int createOrgMember(OrgMemberVo orgMemberVo) throws Exception {
        String gameId = orgMemberVo.getGameId();
        UserVo userVo = userMapper.findUserByUserName(gameId);
        RoleVo roleOrgMaster = roleMapper.findRoleByRoleName("ROLE_ORG_MASTER");
        RoleVo roleUser = roleMapper.findRoleByRoleName("ROLE_ORG_USER");
        if (userVo == null){
            userVo = new UserVo();
            userVo.setUserName(gameId);
            userVo.setPassword(gameId);
            userVo.setGender("0");
        }
        ArrayList<Long> roleIds = new ArrayList<>();
        if (orgMemberVo.getOrgRoleType() == 0 || orgMemberVo.getOrgRoleType() == 1){
            roleIds.add(roleOrgMaster.getId());
            userVo.setUserHasRoleId(roleIds);
        }else {
            roleIds.add(roleUser.getId());
        }
        userVo.setUserHasRoleId(roleIds);
        if (userVo.getId() == null){
            userService.createUser(userVo);
        }else {
            userService.updateUser(userVo);
        }
        orgMemberVo.setSystemUserId(userVo.getId());
        if (orgMemberVo.getId() !=null){
            return orgMemberMapper.updateOrgMemberVo(orgMemberVo);
        }
        int insertOrgMember = orgMemberMapper.insertOrgMember(orgMemberVo);
        fightChangeService.insertFightChange(orgMemberVo);
        return insertOrgMember;
    }

    @Override
    public PageResult listOrgMember(Integer pageIndex, Integer pageSize, Integer isAdmin, String userName) throws Exception {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageIndex == null ? 1 : pageIndex);
        pageRequest.setPageSize(pageSize == null ? 10 : pageSize);
        Page<Object> page = PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), true);
        List<OrgMemberVo> list = null;
        if(isAdmin == 1){
            list = orgMemberMapper.listOrgMemberPageableByAdmin();
            if(list!=null){
                for (OrgMemberVo vo: list){
                    Long fightChangeVal = fightChangeService.getRecentFightVal(vo.getId());
                    vo.setFightChange(fightChangeVal);
                }
            }
        }else {
            OrgMemberVo orgMemberVo = orgMemberMapper.selectByGameId(userName);
            if (orgMemberVo.getOrgId() == null){
                throw new Exception("您还没有加入组织");
            }
            page = PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), true);
            list = orgMemberMapper.listOrgMemberPageable(orgMemberVo.getOrgId());
            if(list!=null){
                for (OrgMemberVo vo: list){
                  Long fightChangeVal = fightChangeService.getRecentFightVal(vo.getId());
                  vo.setFightChange(fightChangeVal);
                }
            }
        }
        PageResult result = new PageResult();
        result.setContent(list);
        result.setTotalSize(page.getTotal());
        result.setPageNum(pageRequest.getPageNum());
        result.setPageSize(pageRequest.getPageSize());
        return result;
    }

    @Override
    public OrgMemberVo getOrgMemberByGameId(OrgMemberVo orgMemberVo) {
        return orgMemberMapper.selectByGameId(orgMemberVo.getGameId());
    }

    @Override
    public int deleteOrgMember(Long id) {
        return orgMemberMapper.deleteOrgMemberById(id);
    }

    @Override
    public OrgMemberVo getMemberInfo(String userName) {
        OrgMemberVo memberInfo = orgMemberMapper.getMemberInfo(userName);
        if (memberInfo == null){
            return null;
        }
        List<FightChangeVo> recentChangeData = fightChangeService.getRecentChangeData(memberInfo.getId());
        memberInfo.setFrightChanges(recentChangeData);
        return memberInfo;
    }
}
