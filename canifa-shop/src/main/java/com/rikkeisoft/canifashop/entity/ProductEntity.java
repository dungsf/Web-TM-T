package com.rikkeisoft.canifashop.entity;

import java.math.BigDecimal;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_products")
public class ProductEntity extends BaseEntity {

	@Column(name = "name", length = 1000, nullable = false)
	private String name;

	@Column(name = "avatar", length = 200, nullable = true)
	private String avatar;

	@Column(name = "price", nullable = false, precision = 13, scale = 2)
	private BigDecimal price;

	@Column(name = "discount", columnDefinition = "INT DEFAULT 0")
	private Integer discount;

	@Column(name = "description", length = 1000, nullable = true)
	private String description;

	@Column(name = "material", length = 1000, nullable = true)
	private String material;

	@Column(name = "tutorial", length = 3000, nullable = true)
	private String tutorial;

	@Column(name = "seo", length = 1000, nullable = false)
	private String seo;

	@Column(name = "is_hot", nullable = true)
	private boolean isHot = Boolean.FALSE;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productEntity")
	private Set<ProductDetailEntity> productDetailEntities;

	public void addProductDetailEntity(ProductDetailEntity productDetailEntity) {
		this.productDetailEntities.add(productDetailEntity);
		productDetailEntity.setProductEntity(this);
	}

	public void deleteProductDetailEntity(ProductDetailEntity productDetailEntity) {
		this.productDetailEntities.remove(productDetailEntity);
		productDetailEntity.setProductEntity(null);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productEntity")
	private Set<ProductImageEntity> productImagesEntities;

	public void addProductImageEntity(ProductImageEntity productImageEntity) {
		this.productImagesEntities.add(productImageEntity);
		productImageEntity.setProductEntity(this);
	}

	public void deleteProductImageEntity(ProductImageEntity productImageEntity) {
		this.productImagesEntities.remove(productImageEntity);
		productImageEntity.setProductEntity(null);

	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private CategoryEntity categoryEntity;

}
