package com.Backend.Config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.Backend.Service.JWTService;
import com.Backend.Service.UserService;
import com.Backend.Service.UserServiceImpl;
import com.Backend.mapper.UserMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthToken extends OncePerRequestFilter{

//	private final HandlerExceptionResolver handlerExceptionResolver;
	private final JWTService jwtService;
	private final UserServiceImpl userService;
	private final UserMapper userMapper;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		final String authHeader = request.getHeader("authorization"); //ver si mayusculas o no
		final String token;
		final String userEmail;
		
		// Next (si no tiene cabecera, sigue la consulta)
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// Recuperar a partir de "Bearer "
		token = authHeader.substring(7);
		
		try {
			userEmail = this.jwtService.extractUsername(token);
			System.out.println("Email en autenticacion: " + userEmail);
			if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userService.loadUserByUsername(userEmail);
				
				if(jwtService.isTokenValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			
			
		} catch (Exception e) {
			response.setStatus(401);
			response.getWriter().write("Invalid or expired token");
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}

}
