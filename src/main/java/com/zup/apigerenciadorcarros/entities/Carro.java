package com.zup.apigerenciadorcarros.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_carro")
public class Carro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String marca;
	private String modelo;
	private Integer ano;
	
	private String diaDeRodizio;
	private boolean statusRodizio;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "carros")
	private List<Usuario> usuarios = new ArrayList<>();
	
	public Carro() {
	}
	
	public Carro(Long id, String marca, String modelo, Integer ano) {
		super();
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public Integer getAno() {
		return ano;
	}


	public void setAno(Integer ano) {
		this.ano = ano;
	}


	public String getDiaDeRodizio() {
		return diaDeRodizio;
	}


	/*public void setDiaDeRodizio(String diaDeRodizio) {
		this.diaDeRodizio = diaDeRodizio;
	}*/


	public boolean isStatusRodizio() {
		return statusRodizio;
	}

	/*public void setStatusRodizio(boolean statusRodizio) {
		this.statusRodizio = statusRodizio;
	}*/
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
