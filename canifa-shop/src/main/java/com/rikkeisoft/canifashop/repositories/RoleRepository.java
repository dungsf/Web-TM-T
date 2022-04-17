package com.rikkeisoft.canifashop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rikkeisoft.canifashop.common.enum_.RoleEnum;
import com.rikkeisoft.canifashop.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	@Query(value = "SELECT * FROM tbl_roles WHERE name = :name", nativeQuery = true)
	List<RoleEntity> findByName(@Param("name") String name);
	
	Boolean existsByName(RoleEnum name);
}
