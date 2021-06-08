package com.zup.apigerenciadorcarros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;

@RestController
@RequestMapping(value = "/api/v1/carros")
public class CarroController {

	@Autowired
	private CarroRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Carro>> findAll(){
		List<Carro> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
	
}
