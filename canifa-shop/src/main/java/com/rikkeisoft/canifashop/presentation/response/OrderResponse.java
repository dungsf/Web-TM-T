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
public class OrderResponse extends BaseResponse{

	private String code;

	private String customerName;

	private String customerAddress;

	private String customerPhone;

	private String customerEmail;

	private String note;

	private BigDecimal total;

	private String orderStatus;

	private List<ProductOrderResponse> listProductOrders;

}
