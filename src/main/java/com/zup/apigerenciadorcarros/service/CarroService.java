package com.zup.apigerenciadorcarros.service;

import java.util.List;

import com.zup.apigerenciadorcarros.entities.Carro;

public interface CarroService {

	public List<Carro> findAll();
	public Carro findById(Long id);
}
