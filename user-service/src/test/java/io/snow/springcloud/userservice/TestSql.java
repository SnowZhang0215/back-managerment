//package io.snow.springcloud.userservice;
//
//import io.snow.springcloud.userservice.dao.PermissionRepository;
//import io.snow.springcloud.userservice.dao.RoleRepository;
//import io.snow.springcloud.userservice.dao.UserRepository;
//import io.snow.springcloud.userservice.entitys.Permission;
//import io.snow.springcloud.userservice.entitys.Role;
//import io.snow.springcloud.userservice.entitys.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.domain.Example;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.util.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class TestSql {
//    private static final Logger logger = LoggerFactory.getLogger(TestSql.class);
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PermissionRepository permissionRepository;
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    private void initSystemRole(){
//
//        roleRepository.deleteAll();
//
//        logger.info("start init system roles");
//
//        Role roleAdmin = new Role();
//        roleAdmin.setCode("ROLE_ADMIN");
//        roleAdmin.setName("管理员");
//        roleRepository.save(roleAdmin);
//
//        Role roleUser = new Role();
//        roleUser.setCode("ROLE_USER");
//        roleUser.setName("用户");
//        roleRepository.save(roleUser);
//
//        logger.info("end init system roles");
//    }
//
//    @Test
//    public void testMenu(){
//
//        Permission permission = new Permission();
//        permission.setCode("index");
//        permission.setDefaultType("0");
//        permission.setIsMenu("1");
//        permission.setLevel("1");
//        permission.setName("首页");
//        permission.setSort(1L);
//        permission.setStatus("1");
//        permission.setUrl("/");
//        permission.setCreatedBy("system");
//        permission.setCreatedDate(Instant.now());
//
//        Permission save = permissionRepository.save(permission);
//
//
//        Permission setting = new Permission();
//        setting.setCode("setting");
//        setting.setDefaultType("0");
//        setting.setIsMenu("1");
//        setting.setLevel("1");
//        setting.setName("设置");
//        setting.setSort(1L);
//        setting.setStatus("1");
//        setting.setUrl("/setting");
//        setting.setCreatedBy("system");
//        setting.setCreatedDate(Instant.now());
//
//        setting = permissionRepository.save(setting);
//
//
//
//
//        Permission theme = new Permission();
//        theme.setCode("themeSetting");
//        theme.setDefaultType("0");
//        theme.setIsMenu("1");
//        theme.setLevel("2");
//        theme.setName("主题设置");
//        theme.setSort(1L);
//        theme.setStatus("1");
//        theme.setUrl("/theme");
//        theme.setCreatedBy("system");
//        theme.setCreatedDate(Instant.now());
//        theme.setParent(setting);
//        Permission save1 = permissionRepository.save(theme);
//
//
//
//
//        List<Permission> allByDefaultType =
//                permissionRepository.findAllByDefaultType();
//
//        logger.info("list : {}",allByDefaultType);
//    }
//
//    @Test
//    public void initSystemUser(){
//        try {
//            initSystemRole();
//            userRepository.deleteAll();
//            User admin = new User();
//            admin.setUserName("admin");
//            admin.setPassword(bCryptPasswordEncoder().encode("admin"));
//            List<Role> allRoles = roleRepository.findAll();
//            logger.info("load all roles : {}" , allRoles.size());
////        Set<Role> set = new HashSet<>(allRoles);
////        admin.setAuthorities(set);
//            admin.setAuthorities(allRoles);
//            User adminSaved = userRepository.save(admin);
//            logger.info("init user : {}",adminSaved.getAuthorities());
//
//            User user = new User();
//            user.setUserName("user");
//            user.setPassword(bCryptPasswordEncoder().encode("user"));
//            List<Role> role_user = roleRepository.findAllByCode("ROLE_USER");
//            logger.info("load user roles : {}" , role_user.size());
////        HashSet<Role> roles = new HashSet<>(userRole);
//            user.setAuthorities(role_user);
//            User userSaved = userRepository.save(user);
//            logger.info("init user : {}",userSaved.getAuthorities());
//        }catch (Exception e){
//            logger.error("init data : {}",e);
//        }
//
//    }
//}
