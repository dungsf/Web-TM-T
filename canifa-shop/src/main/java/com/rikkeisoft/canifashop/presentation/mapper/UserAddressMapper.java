package com.rikkeisoft.canifashop.presentation.mapper;

import com.rikkeisoft.canifashop.entity.UserAddressEntity;
import com.rikkeisoft.canifashop.presentation.request.UserAddressRequest;
import com.rikkeisoft.canifashop.presentation.response.UserAddressResponse;

public class UserAddressMapper {
	private UserAddressMapper() {
		super();
	}

	// convert entity to response
	public static UserAddressResponse convertToResponse(UserAddressEntity entity) {
		return UserAddressResponse.builder().id(entity.getId()).city(entity.getCity()).district(entity.getDistrict())
				.ward(entity.getWard()).phone(entity.getPhone()).detail(entity.getDetail())
				.isDefault(entity.isDefault()).build();
	}

	// convert request to Entity
	public static UserAddressEntity convertToEntity(UserAddressRequest request) {
		return UserAddressEntity.builder().city(request.getCity()).district(request.getDistrict())
				.ward(request.getWard()).phone(request.getPhone()).detail(request.getDetail())
				.isDefault(request.isDefault()).build();
	}
}
