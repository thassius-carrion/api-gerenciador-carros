package com.zup.apigerenciadorcarros.entities.dto.clientfipe;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModeloDTO {

	@JsonProperty("modelos")
	private List<ModeloDTO1> modelos;
	
	@JsonProperty("anos")
	private List<ModeloDTO2> anos;

	public List<ModeloDTO1> getModelos() {
		return modelos;
	}

	public List<ModeloDTO2> getAnos() {
		return anos;
	}
}
