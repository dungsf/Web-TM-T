package com.rikkeisoft.canifashop.presentation.response;

import java.math.BigDecimal;
import java.util.List;

import com.rikkeisoft.canifashop.base.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductResponse extends BaseResponse {

	private String name;

	private String seo;

	private String avatar;

	private String desciption;

	private String material;

	private String tutorial;

	private CategoryResponse category;

	private List<ColorResponse> listColors;

	private List<String> listImages;

	private BigDecimal price;

	private Integer discount;

	private boolean isHot;
}
