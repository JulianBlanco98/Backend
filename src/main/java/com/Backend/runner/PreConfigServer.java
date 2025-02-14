package com.Backend.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PreConfigServer implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Inicializar la tabla de pokemon si está vacía");
		
		
		
	}

	
	
	
}
