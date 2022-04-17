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
import org.springframework.web.multipart.MultipartFile;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.request.ProductRequest;
import com.rikkeisoft.canifashop.presentation.response.ProductResponse;
import com.rikkeisoft.canifashop.services.FileImageService;
import com.rikkeisoft.canifashop.services.ProductService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/admin/products")
@RequiredArgsConstructor
public class ProductAdminController extends BaseController {

	private final ProductService productService;
	private final FileImageService fileImageService;

	@GetMapping
	public ResponseEntity<BasePagerData<ProductResponse>> getProductsByPagination(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "keyword", defaultValue = "-1") String keyword) {
		return ResponseEntity.ok(productService.getProductsByPaging(page, PAGE_SIZE, keyword));
	}

	@GetMapping("/all")
	public ResponseEntity<BaseResponseEntity> getAllProduct() {
		return success(productService.getAll(), "Get all products successful");
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> getProductById(@PathVariable("id") Long id) {
		return success(productService.getById(id), "Get product successful");
	}

	@PostMapping
	public ResponseEntity<BaseResponseEntity> createProduct(@RequestBody ProductRequest request) {
		return created(productService.createOrUpdate(null, request), "Create product successful");
	}

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> updateProduct(@PathVariable("id") Long id,
			@RequestBody ProductRequest request) {
		return success(productService.createOrUpdate(id, request), "Update product successful");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> deleteProduct(@PathVariable("id") Long id) {
		productService.deleteById(id);
		return success("Delete product successful");
	}

	@PostMapping("/uploadfile/{id}")
	public ResponseEntity<BaseResponseEntity> uploadFile(@PathVariable("id") Long id,
			@RequestParam("avatar") MultipartFile avatar, @RequestParam("images") MultipartFile[] images) {

		return created(fileImageService.storeFile(id, avatar, images), "Create images of product successful");
	}

}
