package com.rikkeisoft.canifashop.repositories;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.rikkeisoft.canifashop.entity.OrderEntity;
import com.rikkeisoft.canifashop.presentation.response.statisticalReponse;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	@Query(value = "SELECT * " +
			"FROM tbl_orders " +
			"where month(updated_at) = :month " +
			"And year(updated_at) = :year " +
			"AND order_status = 'COMPLETED'", nativeQuery = true)
	Page<OrderEntity> listOrderByMonth(@Param("year") Integer year, @Param("month") Integer month, Pageable pageable);

	@Query(value = "SELECT * FROM tbl_orders WHERE deleted_flag IS FALSE "
			+ "AND (:keyword IS NULL or (code LIKE %:keyword% OR customer_name LIKE %:keyword%)) ", nativeQuery = true)
	Page<OrderEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	OrderEntity findByCode(String code);

	@Query(value = "SELECT COUNT(id) FROM tbl_orders WHERE deleted_flag IS FALSE", nativeQuery = true)
	Integer countOrders();

	@Query(value = "SELECT SUM(total) from tbl_orders WHERE deleted_flag IS FALSE AND order_status = 'PENDING' AND year(updated_at) = :year and month(updated_at) = :month ", nativeQuery = true)
	BigDecimal getYear(@Param("year") Integer year, @Param("month") Integer month);

	@Query(value = "SELECT MONTH(updated_at) as month, sum(total) as total " +
			"from tbl_orders WHERE deleted_flag IS FALSE " +
			"AND order_status = 'COMPLETED' and year(updated_at) = :year " +
			"group by month(updated_at) " +
			"order by month(updated_at) asc", nativeQuery = true)
	List<statisticalReponse> StatisticalByYear(@Param("year") Integer year);

	@Query(value = "SELECT * FROM tbl_orders WHERE deleted_flag IS FALSE "
			+ "AND user_id IN (SELECT id FROM tbl_users WHERE username = :username) "
			+ "AND (:status IS NULL OR (order_status = :status)) ", nativeQuery = true)
	List<OrderEntity> filterOrderByStatus(@Param("username")String username, @Param("status")String status);

}
