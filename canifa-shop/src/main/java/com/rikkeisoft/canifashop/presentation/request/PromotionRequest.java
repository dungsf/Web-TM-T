package com.rikkeisoft.canifashop.presentation.request;

import java.math.BigDecimal;
import java.sql.Date;
//import java.util.Date;

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
public class PromotionRequest {

	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer discountPercent;
	
	private BigDecimal discountMax;
	
	private BigDecimal discountFrom;
}
