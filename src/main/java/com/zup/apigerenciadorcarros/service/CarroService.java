package com.zup.apigerenciadorcarros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.entities.Usuario;
import com.zup.apigerenciadorcarros.entities.dto.CarroRequestDTO;
import com.zup.apigerenciadorcarros.entities.dto.clientfipe.MarcaDTO;
import com.zup.apigerenciadorcarros.entities.dto.clientfipe.ModeloDTO;
import com.zup.apigerenciadorcarros.entities.dto.clientfipe.ModeloDTO1;
import com.zup.apigerenciadorcarros.repositories.CarroRepository;
import com.zup.apigerenciadorcarros.service.clientfipe.FipeClient;
import com.zup.apigerenciadorcarros.service.exceptions.BadRequestException;
import com.zup.apigerenciadorcarros.service.exceptions.ResourceNotFoundException;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private FipeClient fipeClient;
	
	
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
			Integer codigoModelo = getCodigoModelo(carroRequestDTO.getModelo(), codigoMarca);
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
		List<MarcaDTO> marcas = fipeClient.getMarcas();
		Optional<MarcaDTO> optionalMarca = marcas.stream()
				.filter(m -> compareName(m.getName(), marca))
				.findFirst();
		if(optionalMarca.isPresent()) {
			return optionalMarca.get().getCodigo();
		} 
		throw new BadRequestException();
	}
	
	private Integer getCodigoModelo(String modelo, String codigoMarca) {
		ModeloDTO modelosAnos = fipeClient.getModelosAnos(codigoMarca);
		List<ModeloDTO1> modelos = modelosAnos.getModelos();
		
		Optional<ModeloDTO1> optionalModelo = modelos.stream()
				.filter(m -> compareName(m.getNome(), modelo))
				.findFirst();
		
		if(optionalModelo.isPresent()) {
			return optionalModelo.get().getCodigo();
		} 
		throw new BadRequestException();
	}
	
	/*private String getCodigoAno(String ano, String codigoMarca, Integer codigoModelo) {
		List<MarcaDTO> anosTipos = fipeClient.getAnosTipos(codigoMarca, codigoModelo);
		
	}*/
	
	private boolean compareName(String name1, String name2) {
		return name1.equals(name2);
	}
	
	
	
	
}
