package com.zup.apigerenciadorcarros.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;

@Configuration
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		Carro c1 = new Carro(null, "Fiat", "Palio", 2009);
		Carro c2 = new Carro(null, "Fiat", "Punto", 2012);
		Carro c3 = new Carro(null, "VW", "T-Cross", 2020);
		
		carroRepository.saveAll(Arrays.asList(c1, c2, c3));
		
	}

}
