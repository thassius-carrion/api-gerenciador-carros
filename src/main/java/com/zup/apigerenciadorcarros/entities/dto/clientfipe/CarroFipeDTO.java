package com.zup.apigerenciadorcarros.entities.dto.clientfipe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarroFipeDTO {

	@JsonProperty("Valor")
	private String valor;

	public String getValor() {
		return valor;
	}
}
