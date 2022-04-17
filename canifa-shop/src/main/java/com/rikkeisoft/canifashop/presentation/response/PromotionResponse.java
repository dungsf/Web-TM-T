package com.rikkeisoft.canifashop.presentation.response;

import java.math.BigDecimal;
import java.sql.Date;
//import java.util.Date;

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
public class PromotionResponse extends BaseResponse{

	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer discountPercent;
	
	private BigDecimal discountMax;
	
	private BigDecimal discountFrom;
	
}
