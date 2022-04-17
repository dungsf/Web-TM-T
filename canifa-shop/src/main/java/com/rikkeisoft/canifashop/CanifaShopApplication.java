package com.rikkeisoft.canifashop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.rikkeisoft.canifashop.common.enum_.RoleEnum;
import com.rikkeisoft.canifashop.entity.RoleEntity;
import com.rikkeisoft.canifashop.entity.UserEntity;
import com.rikkeisoft.canifashop.services.RoleService;
import com.rikkeisoft.canifashop.services.UserService;

@SpringBootApplication
public class CanifaShopApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CanifaShopApplication.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CanifaShopApplication.class);
	}
	
	@Bean
    CommandLineRunner run(RoleService roleService, UserService userService) {
        return args -> {
            try {
            	if(!roleService.hasRole(RoleEnum.ROLE_ADMIN)) {
            		roleService.createRole(RoleEntity.builder().name(RoleEnum.ROLE_ADMIN).build());
            	}
            	if(!roleService.hasRole(RoleEnum.ROLE_ADMIN)) {
            		roleService.createRole(RoleEntity.builder().name(RoleEnum.ROLE_USER).build());
            	}
            	if(!userService.hasUsername("admin")) {
            		userService.createAdmin(UserEntity.builder().username("admin").email("admin").password("admin").build());
            	}
            } catch (Exception ignored) {
            }
        };
    }
	
}
