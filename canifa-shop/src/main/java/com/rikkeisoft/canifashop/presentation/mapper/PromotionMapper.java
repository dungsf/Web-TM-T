package com.rikkeisoft.canifashop.presentation.mapper;

import com.rikkeisoft.canifashop.entity.PromotionEntity;
import com.rikkeisoft.canifashop.presentation.request.PromotionRequest;
import com.rikkeisoft.canifashop.presentation.response.PromotionResponse;

public class PromotionMapper {
	public PromotionMapper() {
		super();
	}

	// convert entity to response
	public static PromotionResponse convertToResponse(PromotionEntity entity) {
		return PromotionResponse.builder().id(entity.getId()).name(entity.getName())
				.startDate(entity.getStartDate()).endDate(entity.getEndDate())
				.discountPercent(entity.getDiscountPercent()).discountMax(entity.getDiscountMax())
				.discountFrom(entity.getDiscountFrom()).build();
	}

	// convert request to Entity
	public static PromotionEntity convertToEntity(PromotionRequest request) {
		return PromotionEntity.builder().name(request.getName())
				.startDate(request.getStartDate()).endDate(request.getEndDate())
				.discountFrom(request.getDiscountFrom())
				.discountMax(request.getDiscountMax()).discountPercent(request.getDiscountPercent()).build();
	}

}
