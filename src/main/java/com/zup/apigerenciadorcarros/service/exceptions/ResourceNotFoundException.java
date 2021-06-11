package com.zup.apigerenciadorcarros.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id, String obj) {
		super(obj +" de ID = "+ id +" não encontrado.");
	}
	
}
