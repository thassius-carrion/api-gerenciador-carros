package com.zup.apigerenciadorcarros.service;

import java.util.List;
import java.util.Optional;
import java.lang.IllegalArgumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.apigerenciadorcarros.entities.Carro;
import com.zup.apigerenciadorcarros.entities.Usuario;
import com.zup.apigerenciadorcarros.entities.dto.CarroRequestDTO;
import com.zup.apigerenciadorcarros.entities.dto.clientfipe.CarroFipeDTO;
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
			String valorFipe = getValorFinal(carroRequestDTO);
			Carro carro = toCarro(carroRequestDTO, valorFipe);
			return repository.save(carro);
		} catch (IllegalArgumentException e) {
			throw new BadRequestException();
		}
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
	
	private String getCodigoAno(String toAnoTipo, String codigoMarca, Integer codigoModelo) {
		List<MarcaDTO> anosTipos = fipeClient.getAnosTipos(codigoMarca, codigoModelo);
		Optional<MarcaDTO> optionalAnos = anosTipos.stream()
				.filter(anoTipo -> compareName(toAnoTipo, anoTipo.getCodigo()))
				.findFirst();
		if(optionalAnos.isPresent()) {
			return optionalAnos.get().getCodigo();
		} 
		throw new BadRequestException();
	}
	
	private String getValorFipe(String codigoAno, String codigoMarca, Integer codigoModelo) {
		CarroFipeDTO carroFipeDTO = fipeClient.getCarroFipe(codigoMarca, codigoModelo, codigoAno);
		return carroFipeDTO.getValor();
	}
	
	private String getValorFinal(CarroRequestDTO carroRequestDTO) {
		String codigoMarca = getCodigoMarca(carroRequestDTO.getMarca());
		Integer codigoModelo = getCodigoModelo(carroRequestDTO.getModelo(), codigoMarca);
		String codigoAno = getCodigoAno(toAnoTipo(carroRequestDTO.getAno(), carroRequestDTO.getTipoCombustivel()),
				codigoMarca, codigoModelo);
		return getValorFipe(codigoAno, codigoMarca, codigoModelo);
	}
	
	
	private boolean compareName(String name1, String name2) {
		return name1.equals(name2);
	}
	
	private String toAnoTipo(Integer ano, Integer tipoCombustivel) {
		return ano+"-"+tipoCombustivel;
	}
	
	private Carro toCarro(CarroRequestDTO carroRequestDTO, String valorFipe) {
		Usuario usuario = usuarioService.findById(carroRequestDTO.getUsuarioId());
		return new Carro(null, carroRequestDTO.getMarca(), carroRequestDTO.getModelo(), 
				carroRequestDTO.getAno(), carroRequestDTO.getTipoCombustivel(), valorFipe, usuario);
	}
	
}
