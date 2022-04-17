package com.rikkeisoft.canifashop.presentation.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rikkeisoft.canifashop.entity.ColorEntity;
import com.rikkeisoft.canifashop.entity.ProductDetailEntity;
import com.rikkeisoft.canifashop.entity.ProductEntity;
import com.rikkeisoft.canifashop.entity.SizeEntity;
import com.rikkeisoft.canifashop.presentation.request.ProductRequest;
import com.rikkeisoft.canifashop.presentation.response.ColorResponse;
import com.rikkeisoft.canifashop.presentation.response.ProductResponse;

public class ProductMapper {
	private ProductMapper() {
		super();
	}

	// convert entity to response
	public static ProductResponse convertToResponse(ProductEntity entity) {
		List<ProductDetailEntity> list;
		try {
			list = new ArrayList<>(entity.getProductDetailEntities());
		} catch (Exception e) {
			list = new ArrayList<>();
		}

		Map<ColorEntity, List<ProductDetailEntity>> map = list.stream()
				.collect(Collectors.groupingBy(ProductDetailEntity::getColorEntity));

		List<ColorResponse> listColors = new ArrayList<>();

		map.entrySet().forEach(e -> {
			ColorEntity color = e.getKey();
			List<SizeEntity> listSize = e.getValue().stream().map(ProductDetailEntity::getSizeEntity)
					.collect(Collectors.toList());
			listColors.add(ColorMapper.convertToResponse(color, listSize));
		});

		List<String> listImages;
		try {
			listImages = entity.getProductImagesEntities().stream().map(e -> e.getPath()).collect(Collectors.toList());
		} catch (Exception e) {
			listImages = new ArrayList<>();
		}

		return ProductResponse.builder().id(entity.getId()).name(entity.getName()).seo(entity.getSeo())
				.avatar(entity.getAvatar()).desciption(entity.getDescription()).material(entity.getMaterial())
				.tutorial(entity.getTutorial()).category(CategoryMapper.convertToResponse(entity.getCategoryEntity()))
				.listColors(listColors).listImages(listImages).price(entity.getPrice()).discount(entity.getDiscount())
				.isHot(entity.isHot()).build();
	}

	// convert request to Entity
	public static ProductEntity convertToEntity(ProductRequest request) {
		return ProductEntity.builder().name(request.getName()).price(request.getPrice()).discount(request.getDiscount())
				.description(request.getDesciption()).material(request.getMaterial()).tutorial(request.getTutorial())
				.isHot(request.isHot()).build();
	}

}
