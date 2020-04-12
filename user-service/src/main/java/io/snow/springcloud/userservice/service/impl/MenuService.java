package io.snow.springcloud.userservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.snow.model.vo.ApiVo;
import io.snow.model.vo.Permission;
import io.snow.rest.common.page.PageRequest;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.mapper.PermissionMapper;
import io.snow.springcloud.userservice.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getUserNavMenus(String userName) {
        if ("anonymousUser".equalsIgnoreCase(userName)){
            return permissionMapper.getDefaultPermission();
        }else {
            List<Long> userAllowPermissionIds = permissionMapper.getUserPermissionsIds(userName);
            List<Permission> userMenus = permissionMapper.getUserNavPermissions(userName);
            removeNotAllowChildren(userMenus,userAllowPermissionIds);
            if (userMenus.isEmpty()){
                userMenus = permissionMapper.getDefaultPermission();
            }
            return userMenus;
        }
    }

    /**
     * 移除top菜单中用户不能访问的节点
     * @param userMenus
     * @param userPermission
     */
    private void removeNotAllowChildren(List<Permission> userMenus, List<Long> userPermission) {
        Iterator<Permission> iterator = userMenus.iterator();
        while (iterator.hasNext()){
            Permission permission = iterator.next();
            if (!permission.getChildren().isEmpty()){
                removeNotAllowChildren(permission.getChildren(),userPermission);
            }else {
                if (!userPermission.contains(permission.getId())){
                    //用户权限中没有的菜单，需要移除。
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public List<Permission> getAllParentMenus() {
        return permissionMapper.findAllParentMenus();
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public int createPermission(Permission permission) {
        permission.setCode(permission.getUrl());
        List<Long> permissionApiIds = permission.getPermissionApiIds();
        int i = permissionMapper.insertPermission(permission);
        if (i == 1){
            if (permissionApiIds!=null && permissionApiIds.size()>0){
                Map<String,Object> permissionAndApi = new HashMap<>();
                permissionAndApi.put("permissionId",permission.getId());
                permissionAndApi.put("ApiIds",permissionApiIds);
                permissionMapper.insertPermissionApi(permissionAndApi);
            }
        }
        return i;
    }

    @Override
    public PageResult getSubMenuByParentId(Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum((map.get("pageNum") == null ? 1 : Integer.parseInt(String.valueOf(map.get("pageNum")))));
        pageRequest.setPageSize((map.get("pageSize") == null ? 1 : Integer.parseInt(String.valueOf(map.get("pageSize")))));
        Page<Object> page = PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), true);
        List<Permission> list = permissionMapper.findSubMenusByParentIdPage(map);
        PageResult result = new PageResult();
        result.setContent(list);
        result.setTotalSize(page.getTotal());
        result.setPageNum(pageRequest.getPageNum());
        result.setPageSize(pageRequest.getPageSize());
        return result;
    }

    @Override
    public Permission getPermissionDetail(Long id) {
       return permissionMapper.findPermissionById(id);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public int deletePermission(List<Long> deleteIds) {
        return this.permissionMapper.deletePermission(deleteIds);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public int updatePermission(Permission permission) {
//        List<ApiVo> permissionHasApi = permission.getPermissionHasApi();
        List<Long> permissionApiIds = permission.getPermissionApiIds();
        if(permissionApiIds.isEmpty()){
            permissionMapper.deletePermissionApi(permission.getId());
        }else {
            permissionMapper.deletePermissionApi(permission.getId());
            Map<String,Object> map = new HashMap<>();
            map.put("permissionId",permission.getId());
            map.put("ApiIds",permissionApiIds);
            permissionMapper.insertPermissionApi(map);
        }
        return permissionMapper.updatePermission(permission);
    }

    @Override
    public Permission findPermissionByUrl(String url) {
        return permissionMapper.findPermissionByUrl(url);
    }
}
