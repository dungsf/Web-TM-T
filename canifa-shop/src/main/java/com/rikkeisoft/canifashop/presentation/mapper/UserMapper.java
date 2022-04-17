package com.rikkeisoft.canifashop.presentation.mapper;

import com.rikkeisoft.canifashop.common.enum_.GenderEnum;
import com.rikkeisoft.canifashop.entity.UserEntity;
import com.rikkeisoft.canifashop.presentation.request.UserRequest;
import com.rikkeisoft.canifashop.presentation.response.UserResponse;

public class UserMapper {
	private UserMapper() {
		super();
	}

	// convert entity to response
	public static UserResponse convertToResponse(UserEntity entity) {
		UserResponse response = UserResponse.builder().id(entity.getId()).username(entity.getUsername())
				.firstName(entity.getFirstName()).lastName(entity.getLastName()).phone(entity.getPhone())
				.email(entity.getEmail()).build();

		if (entity.getGender() != null) {
			switch (entity.getGender()) {
			case MALE:
				response.setGender(0);
				break;
			default:
				response.setGender(1);
				break;
			}
		}
		return response;
	}

	// convert request to Entity
	public static UserEntity convertToEntity(UserRequest request) {
		UserEntity entity = UserEntity.builder().username(request.getUsername()).firstName(request.getFirstName())
				.lastName(request.getLastName()).phone(request.getPhone()).email(request.getEmail())
				.password(request.getPassword()).build();

		switch (request.getGender()) {
		case 0:
			entity.setGender(GenderEnum.MALE);
			break;
		case 1:
			entity.setGender(GenderEnum.FEMALE);
			break;
		}

		return entity;
	}
}
