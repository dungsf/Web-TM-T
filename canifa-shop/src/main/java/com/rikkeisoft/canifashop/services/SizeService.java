package com.rikkeisoft.canifashop.services;

import com.rikkeisoft.canifashop.entity.SizeEntity;
import com.rikkeisoft.canifashop.presentation.mapper.SizeMapper;
import com.rikkeisoft.canifashop.presentation.response.SizeResponse;
import com.rikkeisoft.canifashop.repositories.SizeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface SizeService {

	List<SizeResponse> getAll();

	SizeEntity getEntityById(Long id);

}

@Service
@RequiredArgsConstructor
class SizeServiceImpl implements SizeService {

	private final SizeRepository sizeRepository;

	@Override
	public List<SizeResponse> getAll() {
		List<SizeEntity> sizeEntities = sizeRepository.findAll();
		return sizeEntities.stream().map(e -> SizeMapper.convertToResponse(e)).collect(Collectors.toList());
	}

	@Override
	public SizeEntity getEntityById(Long id) {
		return sizeRepository.findById(id).orElse(null);
	}
}
