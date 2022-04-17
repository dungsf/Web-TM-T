package com.rikkeisoft.canifashop.repositories;

import com.rikkeisoft.canifashop.entity.ProductDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {

	@Query(value = "SELECT * FROM tbl_product_detail where color_id = :colorId and product_id = :productId and size_id = :sizeId", nativeQuery = true)
	ProductDetailEntity findByListId(Long colorId, Long productId, Long sizeId);

	@Modifying
	@Query(value = "DELETE FROM tbl_product_detail WHERE product_id = :productId", nativeQuery = true)
	void deleteByProductId(@Param(value = "productId") long productId);

}
