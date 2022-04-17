package com.rikkeisoft.canifashop.presentation.mapper;

import com.rikkeisoft.canifashop.entity.CategoryEntity;
import com.rikkeisoft.canifashop.presentation.request.CategoryRequest;
import com.rikkeisoft.canifashop.presentation.response.CategoryResponse;

public class CategoryMapper {
	private CategoryMapper() {
		super();
	}

	// convert entity to response
	public static CategoryResponse convertToResponse(CategoryEntity entity) {
		if(entity == null) {
			return null;
		}
		if (entity.getParent() == null) {
			return CategoryResponse.builder().id(entity.getId()).name(entity.getName()).seo(entity.getSeo())
					.parent(null).build();
		} else {
			return CategoryResponse.builder().id(entity.getId()).name(entity.getName()).seo(entity.getSeo())
					.parent(convertToResponse(entity.getParent())).build();
		}
	}

	// convert request to Entity
	public static CategoryEntity convertToEntity(CategoryRequest request) {
		return CategoryEntity.builder()
				.name(request.getName())
				.build();
	}

}
