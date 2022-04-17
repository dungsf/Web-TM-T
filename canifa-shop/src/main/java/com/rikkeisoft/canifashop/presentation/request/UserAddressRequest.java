package com.rikkeisoft.canifashop.presentation.request;

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
public class UserAddressRequest {
	
	private int city;

	private int district;

	private int ward;

	private String detail;

	private String phone;

	private boolean isDefault;
	
	private Long userId;
}
