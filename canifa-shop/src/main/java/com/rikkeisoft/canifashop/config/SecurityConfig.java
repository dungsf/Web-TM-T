package com.rikkeisoft.canifashop.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rikkeisoft.canifashop.common.enum_.RoleEnum;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableJpaAuditing
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements AuditorAware<String> {

	@Autowired
	private UserDetailServiceConfig userDetailService;

	@Autowired
	private FilterConfig filterConfig;

	@Value("${base.api}")
	private String BASE_API;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService) // cung cấp userService cho spring security
				.passwordEncoder(passwordEncoder()); // cung cấp password encoder
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(BASE_API + "/auth/**", BASE_API + "/guest/**").permitAll()
				.antMatchers(BASE_API + "/admin/**").hasAuthority(RoleEnum.ROLE_ADMIN.toString())
				.antMatchers(BASE_API + "/user/**").hasAuthority(RoleEnum.ROLE_USER.toString()).anyRequest()
				.authenticated();

		// thêm một lớp Filter kiểm tra jwt
		http.addFilterBefore(filterConfig, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public Optional<String> getCurrentAuditor() {
		try {
			return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
		} catch (Exception ignored) {
			return Optional.of("anonymousUser");
		}
	}
}
