package com.rikkeisoft.canifashop.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rikkeisoft.canifashop.base.BaseEntity;
import com.rikkeisoft.canifashop.common.enum_.OrderStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_orders")
public class OrderEntity extends BaseEntity {

	@Column(name = "code", length = 45, nullable = false)
	private String code;

	@Column(name = "customer_name", length = 100, nullable = true)
	private String customerName;

	@Column(name = "customer_address", length = 200, nullable = true)
	private String customerAddress;

	@Column(name = "customer_phone", length = 10, nullable = true)
	private String customerPhone;

	@Column(name = "customer_email", length = 100, nullable = true)
	private String customerEmail;

	@Column(name = "note", length = 1000, nullable = true)
	private String note;

	@Column(name = "total", precision = 13, scale = 2, nullable = true)
	private BigDecimal total;

	@Column(name = "order_status", length = 20, nullable = true)
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum orderStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "promotions_id")
	private PromotionEntity promotionEntity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderEntity")
	private Set<OrderDetailEntity> orderDetailEntities;

	public void addOrderDetailEntity(OrderDetailEntity orderDetailEntity) {
		this.orderDetailEntities.add(orderDetailEntity);
		orderDetailEntity.setOrderEntity(this);
	}

	public void deleteOrderDetailEntity(OrderDetailEntity orderDetailEntity) {
		this.orderDetailEntities.remove(orderDetailEntity);
		orderDetailEntity.setOrderEntity(null);
	}

}
