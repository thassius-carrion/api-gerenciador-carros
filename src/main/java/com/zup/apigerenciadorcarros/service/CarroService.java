package com.zup.apigerenciadorcarros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.entities.Usuario;
import com.zup.apigerenciadorcarros.entities.dto.CarroRequestDTO;
import com.zup.apigerenciadorcarros.entities.dto.clientfipe.MarcaDTO;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;
import com.zup.apigerenciadorcarros.service.clientfipe.MarcaClient;
import com.zup.apigerenciadorcarros.service.exceptions.BadRequestException;
import com.zup.apigerenciadorcarros.service.exceptions.ResourceNotFoundException;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MarcaClient marcaClient;
	
		public List<Carro> findAll() {
		return repository.findAll();
	}

	public Carro findById(Long id) {
		Optional<Carro> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id, "Carro"));
	}

	public Carro insert(CarroRequestDTO carroRequestDTO) {
		try {
			String codigoMarca = getCodigoMarca(carroRequestDTO.getMarca());
			Carro carro = toCarro(carroRequestDTO);
			carro.getDiaDeRodizio();
			return repository.save(carro);
		} catch (RuntimeException e) {
			throw new BadRequestException();
		}
	}
	
	private Carro toCarro(CarroRequestDTO carroRequestDTO) {
		Usuario usuario = usuarioService.findById(carroRequestDTO.getUsuarioId());
		return new Carro(null, carroRequestDTO.getMarca(), carroRequestDTO.getModelo(), carroRequestDTO.getAno(), usuario);
	}
	
	private String getCodigoMarca(String marca) {
		List<MarcaDTO> marcas = marcaClient.getMarcas();
		Optional<MarcaDTO> optional = marcas.stream()
				//.filter(m -> m.getName().equals(marca))
				.filter(m -> compareName(m.getName(), marca))
				.findFirst();
		if(optional.isPresent()) {
			return optional.get().getCodigo();
		} 
		throw new BadRequestException();
	}
	
	private boolean compareName(String marca1, String marca2) {
		return marca1.equals(marca2);
	}

}
