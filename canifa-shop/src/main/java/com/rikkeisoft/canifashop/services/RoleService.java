package com.rikkeisoft.canifashop.services;

import org.springframework.stereotype.Service;

import com.rikkeisoft.canifashop.common.enum_.RoleEnum;
import com.rikkeisoft.canifashop.entity.RoleEntity;
import com.rikkeisoft.canifashop.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

public interface RoleService {

	void createRole(RoleEntity roleEntity);
	
	boolean hasRole(RoleEnum name);
}

@Service
@RequiredArgsConstructor
class RoleSeviceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Override
	public void createRole(RoleEntity roleEntity) {
		roleRepository.save(roleEntity);
	}

	@Override
	public boolean hasRole(RoleEnum name) {
		return roleRepository.existsByName(name);
	}


}
