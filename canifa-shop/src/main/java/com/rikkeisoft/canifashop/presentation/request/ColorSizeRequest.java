package com.rikkeisoft.canifashop.presentation.request;

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
public class ColorSizeRequest {

	private Long id;

	private List<Long> listSizes;

}
