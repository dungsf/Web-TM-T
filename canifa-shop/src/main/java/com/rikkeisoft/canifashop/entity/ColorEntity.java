package com.rikkeisoft.canifashop.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rikkeisoft.canifashop.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_colors")
public class ColorEntity extends BaseEntity {
	
	@Column(name = "name", length = 45, nullable = false)
	private String name;
	
	@Column(name = "color_code", length = 45, nullable = false)
	private String colorCode;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "colorEntity")
	private Set<ProductDetailEntity> productDetailEntities;
	
	public void addProductDetailEntity(ProductDetailEntity productDetailEntity) {
		this.productDetailEntities.add(productDetailEntity);
		productDetailEntity.setColorEntity(this);
	}
	
	public void deleteProductDetailEntity(ProductDetailEntity productDetailEntity) {
		this.productDetailEntities.remove(productDetailEntity);
		productDetailEntity.setColorEntity(null);
	}

}
