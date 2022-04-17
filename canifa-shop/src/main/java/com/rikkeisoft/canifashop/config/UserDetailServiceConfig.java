package com.rikkeisoft.canifashop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rikkeisoft.canifashop.common.exception.BadRequestException;
import com.rikkeisoft.canifashop.entity.UserEntity;
import com.rikkeisoft.canifashop.repositories.UserRepository;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceConfig implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (Objects.isNull(userEntity)) {
            throw new BadRequestException(username + " not found in database.");
        } else {
            return new CustomUserDetails(userEntity);
        }
    }
}
