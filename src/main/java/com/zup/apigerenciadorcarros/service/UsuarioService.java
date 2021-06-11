package com.zup.apigerenciadorcarros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.apigerenciadorcarros.entities.Usuario;
import com.zup.apigerenciadorcarros.repositories.UsuarioRepository;
import com.zup.apigerenciadorcarros.service.exceptions.BadRequestException;
import com.zup.apigerenciadorcarros.service.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> findAll() {
		return repository.findAll();
	}
	
	public Usuario findById(Long id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() ->  new ResourceNotFoundException(id, "Usuario"));
	}
	
	public Usuario insert(Usuario obj) {
		try {
			return repository.save(obj);
		} catch (RuntimeException e) {
			throw new BadRequestException();
		}
	}
	
	public void refresh(Usuario obj) {
		repository.save(obj);
	}
	
}
