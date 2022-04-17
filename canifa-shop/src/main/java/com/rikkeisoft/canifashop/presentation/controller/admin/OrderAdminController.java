package com.rikkeisoft.canifashop.presentation.controller.admin;

import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.response.statisticalReponse;
import lombok.RequiredArgsConstructor;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.presentation.response.OrderResponse;

import com.rikkeisoft.canifashop.services.OrderService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController extends BaseController {

	private final OrderService orderService;

	@GetMapping
	public ResponseEntity<BasePagerData<OrderResponse>> getOrdersByPagination(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "keyword", defaultValue = "-1") String keyword) {
		return ResponseEntity.ok(orderService.getOrdersByPaging(page, PAGE_SIZE, keyword));
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> getOrderById(@PathVariable Long id){
		return success(orderService.getById(id), "Get Order by successful");
	}

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> updateStatus(@PathVariable Long id,
			@RequestParam("orderStatus") Integer orderStatus) throws MessagingException {
		return success(orderService.updateOrderStatus(id, orderStatus), "Update status order successful");
	}



}
