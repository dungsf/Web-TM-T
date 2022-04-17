package com.rikkeisoft.canifashop.presentation.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.request.UserRequest;
import com.rikkeisoft.canifashop.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/user/profile")
@RequiredArgsConstructor
public class UserController extends BaseController {

	public final UserService userService;

	@PutMapping("/edit/{username}")
	public ResponseEntity<BaseResponseEntity> updateUser(@PathVariable("username") String username,
			@RequestBody UserRequest request) {
		return success(userService.updateUser(username, request), "Update user successful");
	}

	@PutMapping("/edit/password/{username}")
	public ResponseEntity<BaseResponseEntity> updatePasswordByUsername(@PathVariable("username") String username,
			@RequestBody UserRequest request) {
		userService.updatePasswordByUsername(username, request);
		return success("Update password successful");
	}

	@GetMapping("/{username}")
	public ResponseEntity<BaseResponseEntity> getUserByUsername(@PathVariable("username") String username) {
		return success(userService.getUserByUsername(username), "Get user successful");
	}

}
