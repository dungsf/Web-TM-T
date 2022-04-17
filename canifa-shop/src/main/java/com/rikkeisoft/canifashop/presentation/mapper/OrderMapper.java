package com.rikkeisoft.canifashop.presentation.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.rikkeisoft.canifashop.entity.OrderEntity;
import com.rikkeisoft.canifashop.presentation.request.OrderRequest;
import com.rikkeisoft.canifashop.presentation.response.OrderResponse;
import com.rikkeisoft.canifashop.presentation.response.ProductOrderResponse;

public class OrderMapper {
	private OrderMapper() {
		super();
	}

	// convert entity to response
	public static OrderResponse convertToResponse(OrderEntity entity) {
		String orderStatus = null;

		switch (entity.getOrderStatus()) {
		case PENDING:
			orderStatus = "Chờ xác nhận";
			break;
		case RESOLVED:
			orderStatus = "Đang vận chuyển";
			break;
		case COMPLETED:
			orderStatus = "Giao thành công";
			break;
		case CANCELED:
			orderStatus = "Đã hủy";
			break;
		}
		List<ProductOrderResponse> listProductOrders = entity.getOrderDetailEntities().stream()
				.map(e -> ProductOrderMapper.convertToResponse(e)).collect(Collectors.toList());
		return OrderResponse.builder().id(entity.getId()).code(entity.getCode()).createdAt(entity.getCreatedAt())
				.customerName(entity.getCustomerName()).customerAddress(entity.getCustomerAddress())
				.customerPhone(entity.getCustomerPhone()).customerEmail(entity.getCustomerEmail())
				.note(entity.getNote()).total(entity.getTotal()).orderStatus(orderStatus)
				.listProductOrders(listProductOrders).build();
	}
	// convert request to entity
	public static OrderEntity convertToEntity(OrderRequest request) {
		return OrderEntity.builder().customerName(request.getCustomerName())
				.customerAddress(request.getCustomerAddress()).customerPhone(request.getCustomerPhone())
				.customerEmail(request.getCustomerEmail()).note(request.getNote()).total(request.getTotal()).build();
	}

}
