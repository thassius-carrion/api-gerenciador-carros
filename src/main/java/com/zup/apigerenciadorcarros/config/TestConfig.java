package com.zup.apigerenciadorcarros.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.entities.Usuario;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;
import com.zup.apigerenciadorcarros.repositories.UsuarioRepository;

@Configuration
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		
		Carro c1 = new Carro(null, "Fiat", "Palio", 2004);
		Carro c2 = new Carro(null, "Fiat", "Punto", 2012);
		Carro c3 = new Carro(null, "VW", "T-Cross", 2025);
		
		carroRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		//LocalDate.parse("22/07/1996", formato)
		
		Usuario u1 = new Usuario(null, "Thassius Carrion", "thassius@gmail.com", "349.332.468-58", new Date());
		Usuario u2 = new Usuario(null, "Fabio Mendes", "fabio@gmail.com", "165.730.048-09", new Date());
		Usuario u3 = new Usuario(null, "Francisco Silva", "francisco@gmail.com", "344.332.468-58", new Date());
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2, u3));
		
		u1.getCarros().add(c1);
		u1.getCarros().add(c2);
		u3.getCarros().add(c3);
		
		carroRepository.saveAll(Arrays.asList(c1, c2, c3));
		usuarioRepository.saveAll(Arrays.asList(u1, u2, u3));
		
		
	}

}
