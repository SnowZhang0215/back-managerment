package io.snow.springcloud.userservice.dao;

import io.snow.springcloud.userservice.entitys.ApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApiRepository extends JpaRepository<ApiEntity,Long> {

}
