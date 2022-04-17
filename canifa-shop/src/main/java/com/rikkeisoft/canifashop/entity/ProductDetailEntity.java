package com.rikkeisoft.canifashop.entity;

import com.rikkeisoft.canifashop.base.BaseEntity;
import lombok.*;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_product_detail")
public class ProductDetailEntity extends BaseEntity {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productDetailEntity")
	private Set<OrderDetailEntity> orderDetailEntities;

	public void addOrderDetailEntity(OrderDetailEntity orderDetailEntity) {
		this.orderDetailEntities.add(orderDetailEntity);
		orderDetailEntity.setProductDetailEntity(this);
	}

	public void deleteOrderDetailEntity(OrderDetailEntity orderDetailEntity) {
		this.orderDetailEntities.remove(orderDetailEntity);
		orderDetailEntity.setProductDetailEntity(null);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private ProductEntity productEntity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "color_id")
	private ColorEntity colorEntity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "size_id")
	private SizeEntity sizeEntity;

}
