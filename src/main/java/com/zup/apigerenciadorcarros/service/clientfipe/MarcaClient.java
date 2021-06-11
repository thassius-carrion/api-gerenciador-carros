package com.zup.apigerenciadorcarros.service.clientfipe;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.zup.apigerenciadorcarros.entities.dto.clientfipe.MarcaDTO;

@FeignClient(name = "marca.client", url = "${endpoints.fipe.marca}")
public interface MarcaClient {

	@GetMapping
	List<MarcaDTO> getMarcas();
	
}
