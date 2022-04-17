package com.rikkeisoft.canifashop.services;

import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.common.enum_.OrderStatusEnum;
import com.rikkeisoft.canifashop.entity.*;
import com.rikkeisoft.canifashop.presentation.mapper.OrderMapper;
import com.rikkeisoft.canifashop.presentation.request.OrderRequest;
import com.rikkeisoft.canifashop.presentation.response.OrderResponse;
import com.rikkeisoft.canifashop.presentation.response.statisticalReponse;
import com.rikkeisoft.canifashop.repositories.*;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public interface OrderService {

	BasePagerData<OrderResponse> getOrdersByPaging(Integer page, Integer size, String keyword);

	List<OrderResponse> filterOrderByStatus(String username, Integer status);

	OrderResponse updateOrderStatus(Long id, Integer status) throws MessagingException;

	OrderResponse createOrder(OrderRequest orderRequest);

	OrderResponse getById(Long id);

	OrderEntity getEntityById(Long id);

	PromotionEntity getPromotionEntityById(Long id);

	UserEntity getUserEntityById(Long id);

	List<statisticalReponse> getRevenueByYear(Integer year);

	BasePagerData<OrderResponse> getOrderByMonth(Integer page, Integer size, Integer year, Integer month);

//  OrderResponse getByCode(String code);

}

@Service
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final PromotionRepository promotionRepository;
	private final UserRepository userRepository;
	private final ProductDetailRepository productDetailRepository;;

	@Override
	public BasePagerData<OrderResponse> getOrdersByPaging(Integer page, Integer size, String keyword) {

		Pageable paging = PageRequest.of(page, size);

		if (keyword.equals("-1") || keyword.isEmpty()) {
			keyword = null;
		}

		Page<OrderEntity> pagedResult = orderRepository.searchByKeyword(keyword, paging);

		return BasePagerData.build(pagedResult.map(e -> OrderMapper.convertToResponse(e)));
	}

	@Override
	public List<OrderResponse> filterOrderByStatus(String username, Integer orderStatus) {
		String status = null;
		switch (orderStatus) {
		case 0:
			status = OrderStatusEnum.PENDING.toString();
			break;
		case 1:
			status = OrderStatusEnum.RESOLVED.toString();
			break;
		case 2:
			status = OrderStatusEnum.COMPLETED.toString();
			break;
		case 3:
			status = OrderStatusEnum.CANCELED.toString();
			break;
		}
		List<OrderEntity> orderEntities = orderRepository.filterOrderByStatus(username, status);
		return orderEntities.stream().map(e -> OrderMapper.convertToResponse(e)).collect(Collectors.toList());
	}

	@Override
	public OrderResponse createOrder(OrderRequest orderRequest) {
		PromotionEntity promotionEntity = this.getPromotionEntityById(orderRequest.getPromotionId());
		UserEntity userEntity = this.getUserEntityById(orderRequest.getUserId());
		OrderEntity orderEntity = OrderMapper.convertToEntity(orderRequest);
		orderEntity.setCode(String.valueOf(System.currentTimeMillis()));
		orderEntity.setOrderStatus(OrderStatusEnum.PENDING);
		if (userEntity != null) {
			orderEntity.setUserEntity(userEntity);
		}
		if (orderEntity != null) {
			orderEntity.setPromotionEntity(promotionEntity);
		}
		orderEntity.setOrderDetailEntities(new HashSet<>());

		orderRequest.getListOrderDetailsRequest().forEach(id -> {

			ProductDetailEntity productDetailEntity = productDetailRepository.findByListId(id.getColorId(),
					id.getProductId(), id.getSizeId());
			OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder().quantity(id.getQuantity())
					.productDetailEntity(productDetailEntity).price(BigDecimal.valueOf(5.4)).build();
			orderEntity.addOrderDetailEntity(orderDetailEntity);
		});
		return OrderMapper.convertToResponse(orderRepository.save(orderEntity));
	}

	@Override
	public OrderResponse getById(Long id) {
		return OrderMapper.convertToResponse(this.getEntityById(id));
	}

	@Override
	public PromotionEntity getPromotionEntityById(Long id) {
		if (id != null) {
			return promotionRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public UserEntity getUserEntityById(Long id) {
		if (id != null) {
			return userRepository.findById(id).get();
		} else {
			return null;
		}
	}

	@Override
	public OrderResponse updateOrderStatus(Long id, Integer status) throws MessagingException {
		OrderEntity entity = this.getEntityById(id);
		switch (status) {
		case 1:
			entity.setOrderStatus(OrderStatusEnum.RESOLVED);
			break;
		case 2:
			entity.setOrderStatus(OrderStatusEnum.COMPLETED);
			break;
		case 3:
			entity.setOrderStatus(OrderStatusEnum.CANCELED);
			break;
		}

		return OrderMapper.convertToResponse(orderRepository.save(entity));

	}

	@Override
	public OrderEntity getEntityById(Long id) {
		if (id != null) {
			return orderRepository.findById(id).orElse(null);
		} else {
			return null;
		}
	}

	@Override
	public List<statisticalReponse> getRevenueByYear(Integer year) {

		return orderRepository.StatisticalByYear(year);
	}

	@Override
	public BasePagerData<OrderResponse> getOrderByMonth(Integer page, Integer size, Integer year, Integer month) {
		Pageable paging = PageRequest.of(page, size);

		Page<OrderEntity> pagedResult = orderRepository.listOrderByMonth(year, month, paging);

		return BasePagerData.build(pagedResult.map(e -> OrderMapper.convertToResponse(e)));
  }
//	public OrderResponse getByCode(String code) {
//		OrderEntity orderEntity = orderRepository.findByCode(code);
//		return OrderMapper.convertToResponse(orderEntity);
//	}

}
