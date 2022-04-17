package com.rikkeisoft.canifashop.entity;

import com.rikkeisoft.canifashop.base.BaseEntity;
import lombok.*;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "tbl_categories")
public class CategoryEntity extends BaseEntity {

	@Column(name = "name", length = 45, nullable = false)
	private String name;

	@Column(name = "seo", length = 1000, nullable = false)
	private String seo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	private CategoryEntity parent;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
	private Set<CategoryEntity> childs;

	public void addChild(CategoryEntity categoryEntity) {
		this.childs.add(categoryEntity);
		categoryEntity.setParent(this);
	}

	public void deleteChild(CategoryEntity categoryEntity) {
		this.childs.remove(categoryEntity);
		categoryEntity.setParent(null);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categoryEntity")
	private Set<ProductEntity> productEntities;

	public void addProductEntity(ProductEntity productEntity) {
		this.productEntities.add(productEntity);
		productEntity.setCategoryEntity(this);
	}

	public void deleteProductEntity(ProductEntity productEntity) {
		this.productEntities.remove(productEntity);
		productEntity.setCategoryEntity(null);
	}

	public void getListProducts(Set<ProductEntity> list) {
		if (productEntities != null || !productEntities.isEmpty()) {
			list.addAll(productEntities);
		}
		if (childs != null || !childs.isEmpty()) {
			childs.forEach(e -> {
				e.getListProducts(list);
			});
		}
	}

	public void getListCategoryId(Set<Long> listId) {
		listId.add(super.getId());
		if (childs != null || !childs.isEmpty()) {
			childs.forEach(e -> {
				e.getListCategoryId(listId);
			});
		}
	}

}
