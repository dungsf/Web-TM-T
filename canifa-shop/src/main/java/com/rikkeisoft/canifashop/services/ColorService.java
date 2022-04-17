package com.rikkeisoft.canifashop.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.common.exception.BadRequestException;
import com.rikkeisoft.canifashop.entity.ColorEntity;
import com.rikkeisoft.canifashop.presentation.mapper.ColorMapper;
import com.rikkeisoft.canifashop.presentation.request.ColorRequest;
import com.rikkeisoft.canifashop.presentation.response.ColorResponse;
import com.rikkeisoft.canifashop.repositories.ColorRepository;

import lombok.RequiredArgsConstructor;

public interface ColorService {

	List<ColorResponse> getAll();

	ColorResponse createOrUpdate(Long id, ColorRequest request);

	BasePagerData<ColorResponse> getColorsByPaging(Integer page, Integer size, String keyword);

	ColorResponse getById(Long id);

	void deleteById(Long id);

	ColorEntity getEntityById(Long id);

}

@Service
@RequiredArgsConstructor
class ColorServiceImpl implements ColorService {

	public final ColorRepository colorRepository;

	@Override
	public List<ColorResponse> getAll() {
		List<ColorEntity> listColorEntity = colorRepository.findAll();
		List<ColorResponse> listColorResponse = listColorEntity.stream()
				.map(c -> ColorMapper.convertToResponse(c, null)).collect(Collectors.toList());
		return listColorResponse;
	}

	@Override
	public BasePagerData<ColorResponse> getColorsByPaging(Integer page, Integer size, String keyword) {

//		Pageable paging = PageRequest.of(page, size);
//
//		List<SizeEntity> sizeEntities = sizeRepository.findAll();
//
//		Page<ColorEntity> pagedResult = colorRepository.searchByKeyword(keyword, paging);
//
//		Page<ColorEntity> pagedResult = colorRepository.getAllPaging(paging);
//
//		return BasePagerData.build(pagedResult.map(e -> ColorMapper.convertToResponse(e, sizeEntities)));

		Pageable paging = PageRequest.of(page, size);

		if (keyword.equals("-1") || keyword.isEmpty()) {
			keyword = null;
		}

		Page<ColorEntity> pagedResult = colorRepository.searchByKeyword(keyword, paging);

		return BasePagerData.build(pagedResult.map(e -> ColorMapper.convertToResponse(e, null)));
	}

	@Override
	public ColorResponse createOrUpdate(Long id, ColorRequest request) {
		if (id == null || id < 1) {
			ColorEntity entity = ColorMapper.convertToEntity(request);
			colorRepository.save(entity);
			return ColorMapper.convertToResponse(entity, null);
		} else {
			ColorEntity colorEntity = this.getEntityById(id);

			if (colorEntity != null) {
				colorEntity.setName(request.getName());
				colorEntity.setColorCode(request.getCode());
				colorRepository.save(colorEntity);
				return ColorMapper.convertToResponse(colorEntity, null);
			} else {
				return null;
			}
		}
	}

	@Override
	public ColorResponse getById(Long id) {
		ColorEntity colorEntity = colorRepository.getById(id);
		return ColorMapper.convertToResponse(colorEntity, null);
	}

	@Override
	public void deleteById(Long id) {
		ColorEntity entity = this.getEntityById(id);
		if (entity == null) {
			throw new BadRequestException("Color does not exist");
		}
		entity.setDeletedFlag(true);
		colorRepository.save(entity);
	}

	@Override
	public ColorEntity getEntityById(Long id) {
		return colorRepository.findById(id).orElse(null);
	}

}