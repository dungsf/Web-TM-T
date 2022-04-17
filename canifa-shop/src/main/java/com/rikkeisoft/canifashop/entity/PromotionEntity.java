package com.rikkeisoft.canifashop.entity;

import com.rikkeisoft.canifashop.base.BaseEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_promotions")
public class PromotionEntity extends BaseEntity {

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(name = "discount_percent", nullable = false)
	private Integer discountPercent;

	@Column(name = "discount_max", nullable = false, precision = 13, scale = 2)
	private BigDecimal discountMax;

	@Column(name = "discount_from", nullable = false, precision = 13, scale = 2)
	private BigDecimal discountFrom;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "promotionEntity")
	private Set<OrderEntity> orderEntities;

	public void addOrderEntity(OrderEntity orderEntity) {
		this.orderEntities.add(orderEntity);
		orderEntity.setPromotionEntity(this);
	}

	public void deleteOrderEntity(OrderEntity orderEntity) {
		this.orderEntities.remove(orderEntity);
		orderEntity.setPromotionEntity(null);
	}

}
