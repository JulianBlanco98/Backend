package com.Backend.Config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Backend.Exception.InvalidTokenException;
import com.Backend.Exception.NoTokenException;
import com.Backend.Service.JWTService;

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
	private final UserDetailsService userService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		final String authHeader = request.getHeader("authorization"); //ver si mayusculas o no
		final String token;
		final String userEmail;
		
		// Next (si no tiene cabecera, sigue la consulta)
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			/*filterChain.doFilter(request, response);
			return;*/
	        throw new NoTokenException("Token no existe");
		}
		
		// Recuperar a partir de "Bearer "
		token = authHeader.substring(7);
		
		if (token.isBlank()) {
	        throw new NoTokenException("Token no existe");
	    }
		
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
				else {
					throw new InvalidTokenException("Token invalido o expirado");
				}
			}
			
			
		} catch (Exception e) {
			throw new InvalidTokenException("Token invalido o expirado");
		}
		
		filterChain.doFilter(request, response);
		
	}

}
