package com.Backend.Config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
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
		
		
		// Excluir las rutas específicas
	    String path = request.getRequestURI();
	    if (path.equals("/pokemonTGC/users/login") || path.equals("/pokemonTGC/users")) {
	        filterChain.doFilter(request, response); // Dejar pasar sin filtro de autenticación
	        return;
	    }
		
		final String authHeader = request.getHeader("authorization"); //ver si mayusculas o no
		final String token;
		final String userEmail;
		
		try {
			
		// Next (si no tiene cabecera, 401
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
			
			filterChain.doFilter(request, response);
			
		} catch (NoTokenException e) {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"NoTokenException\", \"message\": \"" + e.getMessage() + "\"}");
	    } catch (InvalidTokenException e) {
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"InvalidTokenException\", \"message\": \"" + e.getMessage() + "\"}");
	    }
		
		
	}

}
