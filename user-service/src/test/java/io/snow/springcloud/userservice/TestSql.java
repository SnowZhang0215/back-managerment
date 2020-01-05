package io.snow.springcloud.userservice;

import io.snow.springcloud.userservice.dao.RoleRepository;
import io.snow.springcloud.userservice.dao.UserRepository;
import io.snow.springcloud.userservice.entitys.Role;
import io.snow.springcloud.userservice.entitys.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSql {
    private static final Logger logger = LoggerFactory.getLogger(TestSql.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private void initSystemRole(){

        roleRepository.deleteAll();

        logger.info("start init system roles");

        Role roleAdmin = new Role();
        roleAdmin.setCode("ROLE_ADMIN");
        roleAdmin.setName("管理员");
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setCode("ROLE_USER");
        roleUser.setName("用户");
        roleRepository.save(roleUser);

        logger.info("end init system roles");
    }

    @Test
    public void initSystemUser(){
        try {
            initSystemRole();
            userRepository.deleteAll();
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword(bCryptPasswordEncoder().encode("admin"));
            List<Role> allRoles = roleRepository.findAll();
            logger.info("load all roles : {}" , allRoles.size());
//        Set<Role> set = new HashSet<>(allRoles);
//        admin.setAuthorities(set);
            admin.setAuthorities(allRoles);
            User adminSaved = userRepository.save(admin);
            logger.info("init user : {}",adminSaved.getAuthorities());

            User user = new User();
            user.setUserName("user");
            user.setPassword(bCryptPasswordEncoder().encode("user"));
            List<Role> role_user = roleRepository.findAllByCode("ROLE_USER");
            logger.info("load user roles : {}" , role_user.size());
//        HashSet<Role> roles = new HashSet<>(userRole);
            user.setAuthorities(role_user);
            User userSaved = userRepository.save(user);
            logger.info("init user : {}",userSaved.getAuthorities());
        }catch (Exception e){
            logger.error("init data : {}",e);
        }

    }
}
