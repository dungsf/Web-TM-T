package com.rikkeisoft.canifashop.presentation.request;

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
public class OrderDetailsRequest {

	private Long colorId;

	private Long productId;

	private Long sizeId;

	private Integer quantity;

}
