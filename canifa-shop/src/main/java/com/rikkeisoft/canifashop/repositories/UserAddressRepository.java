package com.rikkeisoft.canifashop.repositories;

import com.rikkeisoft.canifashop.entity.UserAddressEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {
	
	@Query(value = "SELECT * FROM tbl_user_address WHERE user_id IN (SELECT id FROM tbl_users WHERE username = :username)",nativeQuery = true)
	List<UserAddressEntity> getAllAddressByUsername(@Param("username") String username);
	
	@Query(value = "SELECT * FROM tbl_user_address WHERE user_id IN (SELECT id FROM tbl_users WHERE username = :username) AND is_default IS TRUE",nativeQuery = true)
	List<UserAddressEntity> getAddressDefault(@Param("username") String username);
	
	@Modifying
	@Query(value = "UPDATE tbl_user_address SET is_default = 0 WHERE user_id = :userId", nativeQuery = true)
	void setNotDefaultForAddress(@Param("userId") Long userId);
	
}
