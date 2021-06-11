package com.zup.apigerenciadorcarros.entities;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_carro")
public class Carro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(name = "marca")
	@Column(nullable = false)
	private String marca;
	
	@Column(nullable = false)
	private String modelo;
	
	@Column(nullable = false)
	private Integer ano;
	
	private String diaDeRodizio;
	private boolean statusRodizio;
	
	//private Double valorFipe;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "usuario_id")   //, nullable = false
	private Usuario usuario;
	
	public Carro() {
	}
	
	public Carro(Long id, String marca, String modelo, Integer ano, Usuario usuario) {
		super();
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.diaDeRodizio = diaRodizio(ano);
		this.usuario = usuario;
		//this.statusRodizio = getStatusRodizio();
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
		diaDeRodizio = diaRodizio(ano);
		return diaDeRodizio;
	}

	public boolean getStatusRodizio() {
		statusRodizio = temRodizio();
		return statusRodizio;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	//public Double/String getValorFipe(){
	//}

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
	
	private int lastDigit(int n) {
		return (n % 10);
	}

	private String diaRodizio(int n) {
		int v = lastDigit(n);
		String rodizio = "";
		switch(v){
			case 0: case 1: rodizio = "segunda-feira"; break;
			case 2: case 3: rodizio = "terça-feira"; break;
			case 4: case 5: rodizio = "quarta-feira"; break;
			case 6: case 7: rodizio = "quinta-feira"; break;
			case 8: case 9: rodizio = "sexta-feira"; break;
		}
		return rodizio;
	}
	
	private String pegarDiaDeHoje() {
	    DayOfWeek dia = LocalDate.now().getDayOfWeek();
	    return dia.getDisplayName(TextStyle.FULL, Locale.getDefault());
	}
	
	private boolean temRodizio() {
		if(pegarDiaDeHoje().equals(diaDeRodizio)) {
			return true;
		}else {
			return false;
		}
	}
}
