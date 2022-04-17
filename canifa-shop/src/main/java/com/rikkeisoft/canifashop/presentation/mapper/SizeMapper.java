package com.rikkeisoft.canifashop.presentation.mapper;

import com.rikkeisoft.canifashop.entity.SizeEntity;
import com.rikkeisoft.canifashop.presentation.response.SizeResponse;

public class SizeMapper {
	private SizeMapper() {
		super();
	}

	// convert entity to response
	public static SizeResponse convertToResponse(SizeEntity entity) {
		return SizeResponse.builder().id(entity.getId()).name(entity.getName()).build();
	}

}
