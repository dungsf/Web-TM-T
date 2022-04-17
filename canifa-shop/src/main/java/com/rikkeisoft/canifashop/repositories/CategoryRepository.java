package com.rikkeisoft.canifashop.repositories;

import com.rikkeisoft.canifashop.entity.CategoryEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	@Query(value = "SELECT * FROM tbl_categories WHERE deleted_flag IS FALSE", nativeQuery = true)
	List<CategoryEntity> findAll();

	@Query(value = "SELECT id FROM tbl_categories WHERE name LIKE %:name%", nativeQuery = true)
	List<Long> getIdByName(@Param("name") String keyword);

	@Query(value = "SELECT * FROM tbl_categories WHERE deleted_flag IS FALSE "
			+ "AND (:keyword IS NULL or (name LIKE %:keyword% OR parent_id IN (SELECT id FROM tbl_categories WHERE name LIKE %:keyword%))) ", nativeQuery = true)
	Page<CategoryEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM tbl_categories WHERE deleted_flag IS FALSE", nativeQuery = true)
	Page<CategoryEntity> getAllPaging(Pageable pageable);

	List<CategoryEntity> findBySeo(String seo);

	@Query(value = "SELECT COUNT(id) FROM tbl_categories WHERE deleted_flag IS FALSE", nativeQuery = true)
	Integer countCategory();

  @Query(value = "SELECT * FROM tbl_categories WHERE parent_id IS NULL", nativeQuery = true)
	List<CategoryEntity> getAllCategoriesParent();

}
