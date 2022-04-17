package com.rikkeisoft.canifashop.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rikkeisoft.canifashop.common.exception.BadRequestException;
import com.rikkeisoft.canifashop.entity.UserAddressEntity;
import com.rikkeisoft.canifashop.entity.UserEntity;
import com.rikkeisoft.canifashop.presentation.mapper.UserAddressMapper;
import com.rikkeisoft.canifashop.presentation.request.UserAddressRequest;
import com.rikkeisoft.canifashop.presentation.response.UserAddressResponse;
import com.rikkeisoft.canifashop.repositories.UserAddressRepository;
import com.rikkeisoft.canifashop.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

public interface UserAddressService {

	List<UserAddressResponse> getAddressByUsername(String username);

	UserAddressResponse createOrUpdate(Long id, UserAddressRequest request);

	UserAddressResponse getById(Long id);

	void deleteById(Long id);

	UserAddressEntity getEntityById(Long id);

}

@Service
@RequiredArgsConstructor
class UserAddressServiceImpl implements UserAddressService {

	public final UserAddressRepository userAddressRepository;
	private final UserRepository userRepository;

	@Override
	public List<UserAddressResponse> getAddressByUsername(String username) {
		List<UserAddressEntity> userAddressEntity = userAddressRepository.getAllAddressByUsername(username);
		List<UserAddressResponse> listUserResponse = userAddressEntity.stream()
				.map(entity -> UserAddressMapper.convertToResponse(entity)).collect(Collectors.toList());
		return listUserResponse;
	}

	@Override
	public UserAddressResponse createOrUpdate(Long id, UserAddressRequest request) {

		checkAddressIsDefault(request);

		if (id == null || id < 1) {
			Long userId = request.getUserId();
			UserEntity userEntity = userRepository.findById(userId).orElse(null);
			UserAddressEntity entity = UserAddressMapper.convertToEntity(request);
			entity.setUserEntity(userEntity);
			return UserAddressMapper.convertToResponse(userAddressRepository.save(entity));
		} else {
			UserAddressEntity entity = this.getEntityById(id);
			if (entity != null) {
				entity.setCity(request.getCity());
				entity.setDistrict(request.getDistrict());
				entity.setPhone(request.getPhone());
				entity.setDetail(request.getDetail());
				entity.setDefault(request.isDefault());
				return UserAddressMapper.convertToResponse(userAddressRepository.save(entity));
			} else {
				return null;
			}
		}
	}

	@Override
	public UserAddressResponse getById(Long id) {
		return UserAddressMapper.convertToResponse(this.getEntityById(id));
	}

	@Override
	public void deleteById(Long id) {
		if (this.getEntityById(id) == null) {
			throw new BadRequestException("User address does not exist");
		}
		userAddressRepository.deleteById(id);
	}

	@Override
	public UserAddressEntity getEntityById(Long id) {
		return userAddressRepository.findById(id).orElse(null);
	}

	@Transactional
	private void checkAddressIsDefault(UserAddressRequest request) {
		if (request.isDefault()) {
			userAddressRepository.setNotDefaultForAddress(request.getUserId());
		}
	}

}
