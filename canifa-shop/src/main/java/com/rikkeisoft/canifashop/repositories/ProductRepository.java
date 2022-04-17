package com.rikkeisoft.canifashop.repositories;

import com.rikkeisoft.canifashop.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	@Query(value = "SELECT * FROM tbl_products WHERE deleted_flag IS FALSE "
			+ "AND (:keyword IS NULL or (name LIKE %:keyword% OR category_id IN (SELECT id FROM tbl_categories WHERE name LIKE %:keyword%))) ", nativeQuery = true)
	Page<ProductEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM tbl_products WHERE deleted_flag IS FALSE "
			+ "AND (:search IS NULL or (name LIKE %:search%)) "
			+ "AND (:priceMin IS NULL or (price * ((100 - discount) / 100) >= :priceMin)) "
			+ "AND (:priceMax IS NULL or (price * ((100 - discount) / 100) <= :priceMax)) "
			+ "AND (:checkCategory IS NULL or (category_id IN (:listCategoryId))) ", nativeQuery = true)
	Page<ProductEntity> searchByKeyword(@Param("search") String search, @Param("priceMin") BigDecimal priceMin,
			@Param("priceMax") BigDecimal priceMax, @Param("checkCategory") String checkCategory,
			@Param("listCategoryId") Set<Long> listCategoryId, Pageable pageable);

	@Query(value = "SELECT * FROM tbl_products WHERE deleted_flag IS FALSE", nativeQuery = true)
	Page<ProductEntity> getAllPaging(Pageable pageable);

	@Query(value = "SELECT * FROM tbl_products WHERE deleted_flag IS FALSE "
			+ "AND category_id =:categoryId ", nativeQuery = true)
	List<ProductEntity> findByCategoryId(@Param("categoryId") Long categoryId);

	List<ProductEntity> findBySeo(String seo);

	@Query(value = "SELECT * FROM tbl_products WHERE deleted_flag IS FALSE AND is_hot = b'1'", nativeQuery = true)
	List<ProductEntity> findByHot();

  @Query(value = "SELECT COUNT(id) FROM tbl_products WHERE deleted_flag IS FALSE", nativeQuery = true)
	int countProducts();

	@Query(value = "SELECT * FROM tbl_products WHERE deleted_flag = b'0' "
			+ "AND (:checkCategory IS NULL or (category_id IN (:listCategoryId))) ", nativeQuery = true)
	Page<ProductEntity> searchByCategories(String checkCategory, Set<Long> listCategoryId, Pageable paging);
}