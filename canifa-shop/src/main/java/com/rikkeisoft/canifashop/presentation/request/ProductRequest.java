package com.rikkeisoft.canifashop.presentation.request;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

	private String name;

	private MultipartFile avatar;

	private BigDecimal price;

	private Integer discount;

	private String desciption;

	private String material;

	private String tutorial;

	private boolean isHot;

	private Long categoryId;

	private List<ColorSizeRequest> listColors;

}