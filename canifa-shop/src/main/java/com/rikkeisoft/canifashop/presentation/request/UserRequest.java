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
public class UserRequest {

	private String username;

	private String firstName;

	private String lastName;

	private String avatar;

	private String phone;

	private String email;

	private String password;
	
	private String newPassword;

	private int gender;
}
