package com.rikkeisoft.canifashop.presentation.response;

import com.rikkeisoft.canifashop.base.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserAddressResponse extends BaseResponse {

	private int city;

	private int district;

	private int ward;

	private String detail;

	private String phone;

	private boolean isDefault;
}
