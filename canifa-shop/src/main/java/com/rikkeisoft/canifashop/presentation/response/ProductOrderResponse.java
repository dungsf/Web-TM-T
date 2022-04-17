package com.rikkeisoft.canifashop.presentation.response;

import java.math.BigDecimal;

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
public class ProductOrderResponse extends BaseResponse{

	private String name;

	private String size;

	private String color;

	private int quantity;

	private BigDecimal price;

	private BigDecimal priceSale;

}
