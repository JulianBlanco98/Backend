//package com.Backend.Config;
//
//import java.util.List;
//
//import org.apache.catalina.filters.CorsFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
//@EnableWebSecurity
//@Configuration
//public class CorsConfig{
//
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of("http://localhost:4200")); // Permite Angular
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Permite el token en el header
//        config.setExposedHeaders(List.of("Authorization")); // Expone el header Authorization en la respuesta
//		
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        
//        return new CorsFilter();
//        
//	}
//	
//}
