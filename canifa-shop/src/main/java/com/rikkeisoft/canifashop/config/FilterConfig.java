package com.rikkeisoft.canifashop.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikkeisoft.canifashop.base.BaseResponseEntity;
import com.rikkeisoft.canifashop.common.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class FilterConfig extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Value("${jwt.secret}")
	private String JWT_SECRET;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().contains("auth") || request.getServletPath().contains("guest")) {
			filterChain.doFilter(request, response);
		} else {
			response.setContentType(APPLICATION_JSON_VALUE);
			response.setStatus(UNAUTHORIZED.value());
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					DecodedJWT decodedJWT = tokenProvider.decodedJWT(token);
					String type = decodedJWT.getClaim("type").asString();
					if (type.equals("access")) {
						String username = decodedJWT.getSubject();
						String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
						Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
						stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
						UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
								username, null, authorities);
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
						filterChain.doFilter(request, response);
					} else {
						new ObjectMapper().writeValue(response.getOutputStream(),
								BaseResponseEntity.error("Unauthorized", 401));
					}
				} catch (Exception exception) {
					new ObjectMapper().writeValue(response.getOutputStream(),
							BaseResponseEntity.error(exception.getMessage(), 401));
				}
			} else {
				new ObjectMapper().writeValue(response.getOutputStream(), BaseResponseEntity.error("Unauthorized", 401));
			}
		}
	}

}
