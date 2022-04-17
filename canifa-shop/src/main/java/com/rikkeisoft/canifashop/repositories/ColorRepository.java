package com.rikkeisoft.canifashop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rikkeisoft.canifashop.entity.ColorEntity;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {

	@Query(value = "SELECT * FROM tbl_colors WHERE deleted_flag IS FALSE "
			+ "AND (:keyword IS NULL or (name LIKE %:keyword% OR color_code LIKE %:keyword%)) ", nativeQuery = true)
	Page<ColorEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query(value = "SELECT COUNT(id) FROM tbl_colors WHERE deleted_flag IS FALSE", nativeQuery = true)
	int countColors();
}
