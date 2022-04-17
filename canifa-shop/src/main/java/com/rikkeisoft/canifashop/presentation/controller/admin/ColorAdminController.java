package com.rikkeisoft.canifashop.presentation.controller.admin;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.presentation.request.ColorRequest;
import com.rikkeisoft.canifashop.presentation.response.ColorResponse;
import com.rikkeisoft.canifashop.services.ColorService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/admin/colors")
public class ColorAdminController extends BaseController {

	private final ColorService colorService;

	@GetMapping
	public ResponseEntity<BasePagerData<ColorResponse>> getColorsByPaging(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "keyword", defaultValue = "-1") String keyword) {
		return ResponseEntity.ok(colorService.getColorsByPaging(page, PAGE_SIZE, keyword));
	}

	@GetMapping("/all")
	public ResponseEntity<BaseResponseEntity> getAllColor() {
		return success(colorService.getAll(), "Get all colors successful");
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> getColorById(@PathVariable Long id) {
		return success(colorService.getById(id), "Get color successful");
	}

	@PostMapping
	public ResponseEntity<BaseResponseEntity> createColor(@RequestBody ColorRequest request) {
		return created(colorService.createOrUpdate(null, request), "Create color successful");
	}

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> updateColor(@PathVariable("id") Long id,
			@RequestBody ColorRequest request) {
		return success(colorService.createOrUpdate(id, request), "Update color successful");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponseEntity> deleteColor(@PathVariable("id") Long id) {
		colorService.deleteById(id);
		return success("Delete color successful");
	}

}
