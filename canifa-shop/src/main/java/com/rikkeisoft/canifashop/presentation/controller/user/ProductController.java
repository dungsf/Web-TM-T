package com.rikkeisoft.canifashop.presentation.controller.user;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.response.ProductResponse;
import com.rikkeisoft.canifashop.services.FileImageService;
import com.rikkeisoft.canifashop.services.ProductService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequestMapping("${base.api}/guest")
@RequiredArgsConstructor
public class ProductController extends BaseController {

	private final ProductService productService;
	private final FileImageService fileImageService;

	@GetMapping("/products/search")
	public ResponseEntity<BasePagerData<ProductResponse>> getProductsByKeyword(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "search", defaultValue = "-1") String search,
			@RequestParam(name = "priceMin", defaultValue = "-1") BigDecimal priceMin,
			@RequestParam(name = "priceMax", defaultValue = "-1") BigDecimal priceMax,
			@RequestParam(name = "categoryId", defaultValue = "-1") Long categoryId) {

		return ResponseEntity
				.ok(productService.getProductsByKeyword(page, PAGE_SIZE, search, priceMin, priceMax, categoryId));
	}

	@GetMapping("/products/hot")
	public ResponseEntity<BaseResponseEntity> getProductsByHot() {
		return success(productService.getByHot(), "Get list products hot successful");
	}

	@GetMapping("/products/{seo}")
	public ResponseEntity<BaseResponseEntity> getProductBySeo(@PathVariable("seo") String seo) {
		return success(productService.getBySeo(seo), "Get product by seo successful");
	}

	@GetMapping("/image/{id}/{type}/{file}")
	public ResponseEntity<Resource> displayImage(@PathVariable("id") String id, @PathVariable("type") String type,
			@PathVariable("file") String file, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileImageService.loadFileAsResource(id + "/" + type + "/" + file);
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			return null;
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
