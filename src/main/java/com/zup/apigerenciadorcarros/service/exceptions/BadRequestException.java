package com.zup.apigerenciadorcarros.service.exceptions;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super("Inserção mal feita, favor rever os dados inseridos");
	}
	
}
