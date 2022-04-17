package com.rikkeisoft.canifashop.repositories;

import com.rikkeisoft.canifashop.entity.ProductImageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

	@Modifying
	@Query(value = "DELETE FROM tbl_product_images WHERE product_id = :productId", nativeQuery = true)
	void deleteByProductId(@Param(value = "productId") long productId);

}
