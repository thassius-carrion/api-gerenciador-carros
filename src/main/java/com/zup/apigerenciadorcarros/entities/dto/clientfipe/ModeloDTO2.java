package com.zup.apigerenciadorcarros.entities.dto.clientfipe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModeloDTO2 {

	@JsonProperty("nome")
	private String nome; 
	
	@JsonProperty("codigo")
	private String codigo;

	public String getNome() {
		return nome;
	}

	public String getCodigo() {
		return codigo;
	}
}
