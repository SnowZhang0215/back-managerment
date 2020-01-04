package io.snow.springcloud.userservice.dao;

import io.snow.springcloud.userservice.entitys.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

}
