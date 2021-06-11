package com.zup.apigerenciadorcarros.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.entities.Usuario;
import com.zup.apigerenciadorcarros.service.CarroService;
import com.zup.apigerenciadorcarros.service.UsuarioService;

@RestController
@RequestMapping(value = "/api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private CarroService carroService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll(){
		List<Usuario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id) {
		Usuario obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> insert(@RequestBody Usuario obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PostMapping(value = "/{id}/addcarro")
	public ResponseEntity<Carro> insert(@PathVariable Long id, @RequestBody Carro carro) {
		Usuario usuario = service.findById(id);
		carro = carroService.insert(carro);
		carro.setUsuario(usuario);
		service.refresh(usuario);
		URI uri = ServletUriComponentsBuilder.fromPath("/api/v1/carros").path("/{id}").buildAndExpand(carro.getId()).toUri();
		return ResponseEntity.created(uri).body(carro);
	}
}
