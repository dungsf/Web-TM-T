package com.rikkeisoft.canifashop.presentation.controller.user;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.request.OrderRequest;
import com.rikkeisoft.canifashop.services.OrderService;
import lombok.RequiredArgsConstructor;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/user/cart")
@RequiredArgsConstructor
public class CartController extends BaseController {
	private final OrderService orderService;

	@GetMapping("/{username}")
	public ResponseEntity<BaseResponseEntity> filterOrderByStatus(@PathVariable("username") String username,
			@RequestParam("orderStatus") Integer orderStatus) {
		return success(orderService.filterOrderByStatus(username, orderStatus), "Get all order successful");
	}
		
//	@GetMapping("/detail/{code}")
//	public ResponseEntity<BaseResponseEntity> getOrderByCode(@PathVariable("code") String code) throws MessagingException {
//		return success(orderService.getByCode(code), "Get Order by successful");
//	}

	@PostMapping
	public ResponseEntity<BaseResponseEntity> createOrder(@RequestBody OrderRequest orderRequest) {
		return created(orderService.createOrder(orderRequest), "Create order successful");
	}

}
