package com.zup.apigerenciadorcarros.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.zup.apigerenciadorcarros.entities.Usuario;
import com.zup.apigerenciadorcarros.service.UsuarioService;

@Configuration
public class TestConfig implements CommandLineRunner {
		
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public void run(String... args) throws Exception {
		
		Usuario u1 = new Usuario(null, "Thassius Carrion", "thassius@gmail.com", "349.332.468-58", new Date());
		Usuario u2 = new Usuario(null, "Fabio Mendes", "fabio@gmail.com", "165.730.048-09", new Date());
		Usuario u3 = new Usuario(null, "Francisco Silva", "francisco@gmail.com", "344.332.468-58", new Date());
		
		usuarioService.insert(u1);
		usuarioService.insert(u2);
		usuarioService.insert(u3);
		
	}

}
