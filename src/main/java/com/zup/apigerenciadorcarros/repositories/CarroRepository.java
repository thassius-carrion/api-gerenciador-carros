package com.zup.apigerenciadorcarros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zup.apigerenciadorcarros.entities.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long>{

}
