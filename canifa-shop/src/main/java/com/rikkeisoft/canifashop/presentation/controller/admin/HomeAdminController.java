package com.rikkeisoft.canifashop.presentation.controller.admin;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.presentation.response.OrderResponse;
import com.rikkeisoft.canifashop.presentation.response.statisticalReponse;
import com.rikkeisoft.canifashop.repositories.ColorRepository;
import com.rikkeisoft.canifashop.repositories.OrderRepository;
import com.rikkeisoft.canifashop.repositories.ProductRepository;
import com.rikkeisoft.canifashop.repositories.UserRepository;
import com.rikkeisoft.canifashop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/admin/home")
public class HomeAdminController extends BaseController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    @GetMapping("/users")
    public ResponseEntity<?> getCountUser(){
       return ResponseEntity.ok(userRepository.countUsers());
    }
    @GetMapping("/products")
    public ResponseEntity<?> getCountProduct(){
        return ResponseEntity.ok(productRepository.countProducts());
    }
    @GetMapping("/orders")
    public ResponseEntity<?> getCountOrders(){
        return ResponseEntity.ok(orderRepository.countOrders());
    }

    @GetMapping("/getRevenueByYear")
    public ResponseEntity<List<statisticalReponse>> getRevenueByYear (@RequestParam("year") Integer year){
        List<statisticalReponse> list = orderService.getRevenueByYear(year);
        return ResponseEntity.ok(orderService.getRevenueByYear(year));
    }
    @GetMapping("/getOrderByMonth")
	public ResponseEntity<BasePagerData<OrderResponse>> getOrderByYear(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam("year") Integer year,
            @RequestParam("month") Integer month){
		return ResponseEntity.ok(orderService.getOrderByMonth(page, PAGE_SIZE, year, month));
	}
}
