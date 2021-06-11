package com.zup.apigerenciadorcarros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;
import com.zup.apigerenciadorcarros.service.exceptions.BadRequestException;
import com.zup.apigerenciadorcarros.service.exceptions.ResourceNotFoundException;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;

	public List<Carro> findAll() {
		return repository.findAll();
	}

	public Carro findById(Long id) {
		Optional<Carro> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id, "Carro"));
	}

	public Carro insert(Carro obj) {
		try {
			obj.getDiaDeRodizio();
			return repository.save(obj);
		} catch (RuntimeException e) {
			throw new BadRequestException();
		}
	}

}
