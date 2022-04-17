package com.rikkeisoft.canifashop.presentation.request;

import java.math.BigDecimal;
import java.util.List;

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
public class OrderRequest {

	private String customerName;

	private String customerAddress;

	private String customerPhone;

	private String customerEmail;

	private String note;

	private BigDecimal total;

	private Long userId;

	private Long promotionId;
	
	private List<OrderDetailsRequest> listOrderDetailsRequest;
}
