package com.rikkeisoft.canifashop.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.entity.PromotionEntity;
import com.rikkeisoft.canifashop.presentation.mapper.PromotionMapper;
import com.rikkeisoft.canifashop.presentation.request.PromotionRequest;
import com.rikkeisoft.canifashop.presentation.response.PromotionResponse;
import com.rikkeisoft.canifashop.repositories.PromotionRepository;

import lombok.RequiredArgsConstructor;

public interface PromotionService {
	
	List<PromotionResponse> getAll();

	PromotionResponse createOrUpdate(Long id, PromotionRequest request);

	BasePagerData<PromotionResponse> getPromotionsByPaging(Integer page, Integer size, String keyword);

	PromotionResponse getById(Long id);

	boolean deleteById(Long id);

	PromotionEntity getEntityById(Long id);

}

@Service
@RequiredArgsConstructor
class PromotionServiceImpll implements PromotionService {
	public final PromotionRepository promotionRepository;

	@Override
	public List<PromotionResponse> getAll() {
		List<PromotionEntity> listPromotionEntities = promotionRepository.findAll();
		List<PromotionResponse> listPromotionResponse = listPromotionEntities.stream()
				.map(p -> PromotionMapper.convertToResponse(p)).collect(Collectors.toList());
		return listPromotionResponse;
	}

	@Override
	public BasePagerData<PromotionResponse> getPromotionsByPaging(Integer page, Integer size, String keyword) {

		Pageable paging = PageRequest.of(page, size);

		if (keyword.equals("-1") || keyword.isEmpty()) {
			keyword = null;
		}

		Page<PromotionEntity> pagedResult = promotionRepository.searchByKeyword(keyword, paging);

		return BasePagerData.build(pagedResult.map(e -> PromotionMapper.convertToResponse(e)));
	}

	@Override
	public PromotionResponse createOrUpdate(Long id, PromotionRequest request) {
		if (id == null || id < 1) {
			PromotionEntity entity = PromotionMapper.convertToEntity(request);
			promotionRepository.save(entity);
			return PromotionMapper.convertToResponse(entity);
		} else {
			PromotionEntity promotionEntity = this.getEntityById(id);

			if (promotionEntity != null) {
				promotionEntity.setName(request.getName());
				promotionEntity.setStartDate(request.getStartDate());
				promotionEntity.setEndDate(request.getEndDate());
				promotionEntity.setDiscountPercent(request.getDiscountPercent());
				promotionEntity.setDiscountMax(request.getDiscountMax());
				promotionEntity.setDiscountFrom(request.getDiscountFrom());
				promotionRepository.save(promotionEntity);
				return PromotionMapper.convertToResponse(promotionEntity);
			} else {
				return null;
			}
		}
	}

	@Override
	public PromotionResponse getById(Long id) {
		return PromotionMapper.convertToResponse(this.getEntityById(id));
	}

	@Override
	public boolean deleteById(Long id) {
		if (this.getEntityById(id) != null) {
			promotionRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public PromotionEntity getEntityById(Long id) {
		PromotionEntity entity = promotionRepository.findById(id).get();
		return entity;
	}

//	@Override
//	public List<PromotionResponse> searchByKeyword(String keyword) {
//		List<PromotionEntity> listPromotionEntity = promotionRepository.findByKeyword(keyword);
//		List<PromotionResponse> listPromotionResponse = listPromotionEntity.stream()
//				.map(p -> PromotionMapper.convertToResponse(p)).collect(Collectors.toList());
//		return listPromotionResponse;
//	}

//	@Override
//	public Page<PromotionResponse> findByPagination(int pageNo, int size) {
//		Pageable pageable = PageRequest.of(pageNo - 1, size);
//		Page<PromotionEntity> pagePromotionEntity = promotionRepository.findAll(pageable);
//		Page<PromotionResponse> pagePromotionResponse = pagePromotionEntity
//				.map(e -> PromotionMapper.convertToResponse(e));
//		return pagePromotionResponse;
//	}
}