package com.rikkeisoft.canifashop.presentation.controller.user;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.response.ProductResponse;
import com.rikkeisoft.canifashop.services.CategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/guest/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {

	private final CategoryService categoryService;

	@GetMapping("/seo/{seo}")
	public ResponseEntity<BasePagerData<ProductResponse>> getProductsByCategory(@PathVariable("seo") String seo,
			@RequestParam(name = "page", defaultValue = "0") Integer page) {
		return ResponseEntity.ok(categoryService.getProductByCategory(page, PAGE_SIZE, seo));
	}

	@GetMapping("/parent")
	public ResponseEntity<BaseResponseEntity> getCategoryParent() {
		return success(categoryService.getCategoryParent(), "Get categories parent successful");
	}

}