package com.zup.apigerenciadorcarros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;
	
	public List<Carro> findAll() {
		return repository.findAll();
	}
	
	public Carro findById(Long id) {
		Optional<Carro> obj = repository.findById(id);
		return obj.get();
	}
	
	public Carro insert(Carro obj) {
		obj.getDiaDeRodizio();
		return repository.save(obj);
	}
	
}
