package com.rikkeisoft.canifashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rikkeisoft.canifashop.entity.SizeEntity;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long> {
	@Query(value = "select * from tbl_sizes where name like %:name%", nativeQuery = true)
	List<SizeEntity> findByName(String name);
}
