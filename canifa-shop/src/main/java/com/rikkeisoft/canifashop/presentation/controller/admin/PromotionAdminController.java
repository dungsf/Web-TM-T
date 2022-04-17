package com.rikkeisoft.canifashop.presentation.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.request.PromotionRequest;
import com.rikkeisoft.canifashop.presentation.response.PromotionResponse;
import com.rikkeisoft.canifashop.services.PromotionService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/admin/promotions")
@RequiredArgsConstructor
public class PromotionAdminController extends BaseController {

	private final PromotionService promotionService;

	@GetMapping
	public ResponseEntity<BasePagerData<PromotionResponse>> getPromotionsByPaging(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "keyword", defaultValue = "-1") String keyword) {
		return ResponseEntity.ok(promotionService.getPromotionsByPaging(page, PAGE_SIZE, keyword));
	}

	@GetMapping("/all")
	public ResponseEntity<BaseResponseEntity> getAllPromotions() {
		return success(promotionService.getAll(), "Get all promotions successful");
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> getPromotionById(@PathVariable("id") Long id) {
		return success(promotionService.getById(id), "Get promotion successful");
	}

	@PostMapping
	public ResponseEntity<BaseResponseEntity> createPromotion(@RequestBody PromotionRequest request) {
		return created(promotionService.createOrUpdate(null, request), "Create promotion successful");
	}

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> updatePromotion(@PathVariable("id") Long id,
			@RequestBody PromotionRequest request) {
		return success(promotionService.createOrUpdate(id, request), "Update promotion successful");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> deletePromotion(@PathVariable("id") Long id) {
		promotionService.deleteById(id);
		return success("Delete promotion successful");
	}

}
