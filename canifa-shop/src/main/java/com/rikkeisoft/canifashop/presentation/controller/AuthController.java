package com.rikkeisoft.canifashop.presentation.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Objects;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rikkeisoft.canifashop.base.BaseController;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.common.exception.BadRequestException;
import com.rikkeisoft.canifashop.common.jwt.JwtTokenProvider;
import com.rikkeisoft.canifashop.presentation.request.LoginRequest;
import com.rikkeisoft.canifashop.presentation.request.SignupRequest;
import com.rikkeisoft.canifashop.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${domain.origins}")
@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/auth")
public class AuthController extends BaseController {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService detailsService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@GetMapping("/infor")
	public ResponseEntity<BaseResponseEntity> getInforUser(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			try {
				String token = authorizationHeader.substring("Bearer ".length());
				String username = jwtTokenProvider.getUserNameFromJwtToken(token);
				return success(userService.getUserByUsername(username), "Get user successful");
			} catch (Exception exception) {
				return null;
			}
		}
		return null;
	}

	@PostMapping("/login")
	public ResponseEntity<BaseResponseEntity> authenticateUser(@RequestBody LoginRequest loginRequest,
			HttpServletRequest request) {
		UserDetails userDetails = detailsService.loadUserByUsername(loginRequest.getUsername());
		authenticate(userDetails.getUsername(), loginRequest.getPassword());
		String accessToken = jwtTokenProvider.generateAccessToken(userDetails, request);
		return success(accessToken, "Login successful");
	}

	@PostMapping("/register")
	public ResponseEntity<BaseResponseEntity> registerUser(@RequestBody SignupRequest request) {
		return created(userService.createUser(request), "User registered successfully!");
	}

	@PostMapping("/reset-password")
	public ResponseEntity<BaseResponseEntity> resetPassword(@RequestParam("email") String email)
			throws MessagingException {
		userService.resetPassword(email);
		return success("reset password successful");
	}

	private void authenticate(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			throw new BadRequestException("Incorrect password");
		}
	}

}
