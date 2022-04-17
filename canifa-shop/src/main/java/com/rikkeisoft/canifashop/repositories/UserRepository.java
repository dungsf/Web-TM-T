package com.rikkeisoft.canifashop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rikkeisoft.canifashop.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "SELECT * FROM tbl_users WHERE "
			+ "AND (:keyword IS NULL or (username LIKE %:keyword% OR first_name LIKE %:keyword% OR last_name LIKE %:keyword%)) ", nativeQuery = true)
	Page<UserEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query(value = "SELECT * FROM tbl_users WHERE email =:keyword or username =:keyword ", nativeQuery = true)
	UserEntity findbyEmailorUsername(@Param("keyword") String keyword);

	UserEntity findByUsername(String username);

	@Query(value = "SELECT id FROM tbl_users WHERE username =:username ", nativeQuery = true)
	List<Long> getIdByUsername(@Param("username") String username);
	@Query(value = "SELECT COUNT(id) FROM tbl_users WHERE deleted_flag IS FALSE", nativeQuery = true)
	int countUsers();
}
