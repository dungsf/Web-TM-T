package com.rikkeisoft.canifashop.services;

import java.util.HashSet;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.common.enum_.GenderEnum;
import com.rikkeisoft.canifashop.common.enum_.RoleEnum;
import com.rikkeisoft.canifashop.common.exception.BadRequestException;
import com.rikkeisoft.canifashop.entity.RoleEntity;
import com.rikkeisoft.canifashop.entity.UserEntity;
import com.rikkeisoft.canifashop.presentation.mapper.UserMapper;
import com.rikkeisoft.canifashop.presentation.request.SignupRequest;
import com.rikkeisoft.canifashop.presentation.request.UserRequest;
import com.rikkeisoft.canifashop.presentation.response.UserResponse;
import com.rikkeisoft.canifashop.repositories.RoleRepository;
import com.rikkeisoft.canifashop.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import javax.mail.MessagingException;

public interface UserService {

	void createAdmin(UserEntity entity);

	UserResponse createUser(SignupRequest request);

	UserResponse updateUser(String userName, UserRequest request);

	BasePagerData<UserResponse> getUsersByPaging(Integer page, Integer size, String keyword);

	UserResponse getById(Long id);

	void deleteById(Long id);

	UserEntity getEntityById(Long id);

	void resetPassword(String name) throws MessagingException;

	void lockById(Long id);

	void unlockById(Long id);

	void updatePasswordByUsername(String username, UserRequest request);

	UserResponse getUserByUsername(String userName);

	boolean hasUsername(String username);

}

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

	public final UserRepository userRepository;
	public final PasswordEncoder passwordEncoder;
	public final RoleRepository roleRepository;
	public final EmailService emailService;

	@Override
	public void createAdmin(UserEntity entity) {
		entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
		RoleEntity role = roleRepository.findByName(RoleEnum.ROLE_ADMIN.toString()).get(0);
		entity.setRoleEntities(new HashSet<RoleEntity>());
		entity.addRoleEntity(role);
		userRepository.save(entity);
	}

	@Override
	public UserResponse createUser(SignupRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new BadRequestException("User name already exist");
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new BadRequestException("User email already exist");
		}
		// Create new user's account
		String password = new BCryptPasswordEncoder().encode(request.getPassword());
		UserEntity userEntity = UserEntity.builder().username(request.getUsername()).email(request.getEmail())
				.password(password).build();
		RoleEntity role = roleRepository.findByName(RoleEnum.ROLE_USER.toString()).get(0);
		userEntity.setRoleEntities(new HashSet<RoleEntity>());
		userEntity.addRoleEntity(role);
		UserResponse userResponse = UserMapper.convertToResponse(userRepository.save(userEntity));
		return userResponse;
	}

	@Override
	public UserResponse updateUser(String username, UserRequest request) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity != null) {
			userEntity.setFirstName(request.getFirstName());
			userEntity.setLastName(request.getLastName());
			userEntity.setPhone(request.getPhone());
			userEntity.setEmail(request.getEmail());

			switch (request.getGender()) {
			case 0:
				userEntity.setGender(GenderEnum.MALE);
				break;
			case 1:
				userEntity.setGender(GenderEnum.FEMALE);
				break;
			}
			userRepository.save(userEntity);
			return UserMapper.convertToResponse(userEntity);
		} else {
			return null;
		}
	}

	@Override
	public BasePagerData<UserResponse> getUsersByPaging(Integer page, Integer size, String keyword) {

		Pageable paging = PageRequest.of(page, size);

		if (keyword.equals("-1") || keyword.isEmpty()) {
			keyword = null;
		}

		Page<UserEntity> pagedResult = userRepository.searchByKeyword(keyword, paging);

		return BasePagerData.build(pagedResult.map(e -> UserMapper.convertToResponse(e)));
	}

	@Override
	public UserResponse getById(Long id) {
		return UserMapper.convertToResponse(this.getEntityById(id));
	}

	@Override
	public void lockById(Long id) {
		UserEntity entity = this.getEntityById(id);
		if (entity == null) {
			throw new BadRequestException("User does not exist");
		}
		entity.setDeletedFlag(true);
		userRepository.save(entity);
	}

	@Override
	public void unlockById(Long id) {
		UserEntity entity = this.getEntityById(id);
		if (entity == null) {
			throw new BadRequestException("User does not exist");
		}
		entity.setDeletedFlag(false);
		userRepository.save(entity);
	}

	@Override
	public void deleteById(Long id) {
		UserEntity entity = this.getEntityById(id);
		if (entity == null) {
			throw new BadRequestException("User does not exist");
		}
		userRepository.delete(entity);
	}

	@Override
	public UserEntity getEntityById(Long id) {
		UserEntity entity = userRepository.findById(id).get();
		return entity;
	}

	@Override
	public void resetPassword(String name) throws MessagingException {
		UserEntity entity = userRepository.findbyEmailorUsername(name);
		if (entity == null) {
			throw new BadRequestException("account does not exist");
		}
		String code = RandomStringUtils.randomAlphanumeric(6);
		String password = new BCryptPasswordEncoder().encode("123456");
		entity.setPassword(password);
		userRepository.save(entity);
		emailService.sendResetPassword(entity.getEmail(), entity.getFirstName() + " " + entity.getLastName(), code);
	}

	// đổi mật khẩu
	@Override
	public void updatePasswordByUsername(String username, UserRequest request) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new BadRequestException("User name already exist");
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new BadRequestException("User email already exist");
		}
		try {
			boolean matches = new BCryptPasswordEncoder().matches(request.getPassword(), userEntity.getPassword());
			if (matches) {
				String password = new BCryptPasswordEncoder().encode(request.getNewPassword());
				userEntity.setPassword(password);
				userRepository.save(userEntity);
			} else {
				throw new BadRequestException("Password the same");
			}
		} catch (Exception ex) {
			throw new BadRequestException("User does not exist");
		}

	}

	@Override
	public UserResponse getUserByUsername(String userName) {
		UserEntity userEntity = userRepository.findByUsername(userName);
		return UserMapper.convertToResponse(userEntity);
	}

	@Override
	public boolean hasUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}
