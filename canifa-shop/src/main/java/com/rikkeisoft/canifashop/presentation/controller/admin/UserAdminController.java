package com.rikkeisoft.canifashop.presentation.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.response.UserResponse;
import com.rikkeisoft.canifashop.services.UserService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/admin/users")
@RequiredArgsConstructor
public class UserAdminController extends BaseController {
	
	public final UserService userService;

	@GetMapping
	public ResponseEntity<BasePagerData<UserResponse>> getUsersByPaging(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "keyword", defaultValue = "-1") String keyword) {
		return ResponseEntity.ok(userService.getUsersByPaging(page, PAGE_SIZE, keyword));
	}

	@PutMapping("/lock/{id}")
	public ResponseEntity<BaseResponseEntity> lockUser(@PathVariable("id") Long id) {
		userService.lockById(id);
		return success("Lock user successful");
	}
	
	@PutMapping("/unlock/{id}")
	public ResponseEntity<BaseResponseEntity> unlockUser(@PathVariable("id") Long id) {
		userService.unlockById(id);
		return success("Unlock user successful");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> deleteUser(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return success("Delete user successful");
	}

}
