package com.rikkeisoft.canifashop.presentation.response;


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
public class ColorResponse extends BaseResponse{

	private String name;

	private String code;

	private List<SizeResponse> listSizes;

}
