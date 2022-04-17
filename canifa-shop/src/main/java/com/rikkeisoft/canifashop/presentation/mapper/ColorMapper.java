package com.rikkeisoft.canifashop.presentation.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rikkeisoft.canifashop.entity.ColorEntity;
import com.rikkeisoft.canifashop.entity.SizeEntity;
import com.rikkeisoft.canifashop.presentation.request.ColorRequest;
import com.rikkeisoft.canifashop.presentation.response.ColorResponse;
import com.rikkeisoft.canifashop.presentation.response.SizeResponse;

public class ColorMapper {
	private ColorMapper() {
		super();
	}

	// convert entity to response
	public static ColorResponse convertToResponse(ColorEntity entity, List<SizeEntity> list) {

		List<SizeResponse> listSizes = new ArrayList<>();

		if (list != null) {
			listSizes = list.stream().map(e -> SizeMapper.convertToResponse(e)).collect(Collectors.toList());
		}

		return ColorResponse.builder().id(entity.getId()).name(entity.getName()).code(entity.getColorCode())
				.listSizes(listSizes).build();
	}

	// convert request to Entity
	public static ColorEntity convertToEntity(ColorRequest request) {
		return ColorEntity.builder().name(request.getName()).colorCode(request.getCode()).build();
	}
}
