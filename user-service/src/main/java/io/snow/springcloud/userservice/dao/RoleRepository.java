package io.snow.springcloud.userservice.dao;

import io.snow.springcloud.userservice.entitys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
