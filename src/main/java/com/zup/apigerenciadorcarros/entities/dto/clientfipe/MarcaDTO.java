package com.zup.apigerenciadorcarros.entities.dto.clientfipe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarcaDTO {

	@JsonProperty("nome")
	private String name;
	
	@JsonProperty("codigo")
	private String codigo;

	public String getName() {
		return name;
	}

	public String getCodigo() {
		return codigo;
	}
}
