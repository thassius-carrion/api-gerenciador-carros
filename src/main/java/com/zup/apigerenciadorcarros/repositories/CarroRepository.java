package com.zup.apigerenciadorcarros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zup.apigerenciadorcarros.entities.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

}
