package com.zup.apigerenciadorcarros.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;
import com.zup.apigerenciadorcarros.service.CarroService;

@Service
public class CarroServiceImpl implements CarroService{

	@Autowired
	private CarroRepository repository;
	
	@Override
	public List<Carro> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Carro findById(Long id) {
		Optional<Carro> obj = repository.findById(id);
		return obj.get();
	}
	
}
