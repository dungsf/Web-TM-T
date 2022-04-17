package com.rikkeisoft.canifashop.repositories;

import com.rikkeisoft.canifashop.entity.PromotionEntity;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {

	@Query(value = "SELECT * FROM tbl_promotions WHERE deleted_flag IS FALSE "
			+ "AND (:keyword IS NULL or (name LIKE %:keyword%)) ", nativeQuery = true)
	Page<PromotionEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
