package com.zup.apigerenciadorcarros.service.clientfipe;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zup.apigerenciadorcarros.entities.dto.clientfipe.MarcaDTO;
import com.zup.apigerenciadorcarros.entities.dto.clientfipe.ModeloDTO;

@FeignClient(name = "marca.client", url = "${endpoints.fipe.marca}")
public interface FipeClient {

	@GetMapping
	List<MarcaDTO> getMarcas();
	
	@GetMapping("/{codigo}/modelos")
	ModeloDTO getModelosAnos(@PathVariable String codigo);
	
	@GetMapping("/{codigo1}/modelos/{codigo2}/anos")
	List<MarcaDTO> getAnosTipos(@PathVariable String codigo1, @PathVariable Integer codigo2);
}
