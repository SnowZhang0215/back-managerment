package io.snow.springcloud.userservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.snow.model.vo.RoleVo;
import io.snow.rest.common.page.PageRequest;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.mapper.RoleMapper;
import io.snow.springcloud.userservice.service.IRoleService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("roleService")
public class RoleService implements IRoleService {

    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult getAllRoles(Integer pageIndex, Integer pageSize) {

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageIndex == null ? 1 : pageIndex);
        pageRequest.setPageSize(pageSize == null ? 10 : pageSize);
        Page<Object> page = PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize(), true);
        List<RoleVo> list = roleMapper.listRolePageable();
        PageResult result = new PageResult();
        result.setContent(list);
        result.setTotalSize(page.getTotal());
        result.setPageNum(pageRequest.getPageNum());
        result.setPageSize(pageRequest.getPageSize());
        return result;

    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public int createRole(RoleVo roleVo) {
        return roleMapper.insertRole(roleVo);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public int deleteRoles(List<Long> deleteIds) {
        return 0;
    }

    @Override
    public RoleVo getRoleById(Long id) {
        return null;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public Map<String, Object> authRolePermission(Map<String, Object> map) {
        logger.info("auth role:{}" , map);
        Object role = map.get("roleId");
        Long roleId = Long.parseLong(String.valueOf(role));
        List checks = (List) map.get("checks");
        List<Long> checkIds = new ArrayList<>();
        for (int i = 0; i < checks.size(); i++) {
           LinkedHashMap map1 = (LinkedHashMap) checks.get(i);
            long id = Long.parseLong(String.valueOf(map1.get("id")));
            checkIds.add(id);
        }
        roleMapper.deleteRolePermission(roleId);
        Map<String,Object> param = new HashMap<>();
        param.put("roleId",roleId);
        param.put("checkIds",checkIds);
        if (checkIds.size()>0){
            roleMapper.insertRolePermission(param);
        }
        return param;
    }

    @Override
    public List<Long> getRolePermission(Long rId) {
        return roleMapper.findRolePermissionId(rId);
    }

    @Override
    public List<RoleVo> getAll() {
        return roleMapper.listRolePageable();
    }
}
