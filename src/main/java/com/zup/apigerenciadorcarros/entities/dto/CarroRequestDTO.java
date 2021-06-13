package com.zup.apigerenciadorcarros.entities.dto;

public class CarroRequestDTO {

	private Long usuarioId;
	private String marca;
	private String modelo;
	private Integer ano;
	private Integer tipoCombustivel;
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	public String getMarca() {
		return marca;
	}
	public String getModelo() {
		return modelo;
	}
	public Integer getAno() {
		return ano;
	}
	public Integer getTipoCombustivel() {
		return tipoCombustivel;
	}	
}
