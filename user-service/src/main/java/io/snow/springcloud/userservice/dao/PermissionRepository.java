package io.snow.springcloud.userservice.dao;

import io.snow.springcloud.userservice.entitys.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    @Query(value = "select p from Permission p where p.parent = null and p.defaultType = '0' ")
    List<Permission> findAllByDefaultType();

//    @Query(value = "select p from Permission p where p.parent = null and p.id in ")
//    List<Permission> getUserPermission(@Param("userId") String userId);
}
