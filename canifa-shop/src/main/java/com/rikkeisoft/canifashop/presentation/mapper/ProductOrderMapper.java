package com.rikkeisoft.canifashop.presentation.mapper;

import com.rikkeisoft.canifashop.entity.OrderDetailEntity;
import com.rikkeisoft.canifashop.presentation.response.ProductOrderResponse;

public class ProductOrderMapper {
	private ProductOrderMapper() {
		super();
	}

	// convert entity to response
	public static ProductOrderResponse convertToResponse(OrderDetailEntity entity) {

		return ProductOrderResponse.builder().name(entity.getProductDetailEntity().getProductEntity().getName())
				.size(entity.getProductDetailEntity().getSizeEntity().getName())
				.color(entity.getProductDetailEntity().getColorEntity().getName()).quantity(entity.getQuantity())
				.price(entity.getProductDetailEntity().getProductEntity().getPrice()).build();
	}

}
