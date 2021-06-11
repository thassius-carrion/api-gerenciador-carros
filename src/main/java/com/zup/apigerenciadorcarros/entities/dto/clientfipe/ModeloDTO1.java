package com.zup.apigerenciadorcarros.entities.dto.clientfipe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModeloDTO1 {

	@JsonProperty("nome")
	private String nome; 
	
	@JsonProperty("codigo")
	private Integer codigo;

	public String getNome() {
		return nome;
	}

	public Integer getCodigo() {
		return codigo;
	}
}
