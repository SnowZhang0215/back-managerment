package io.snow.springcloud.userservice.dao;

import io.snow.springcloud.userservice.entitys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByCode(String code);
}
