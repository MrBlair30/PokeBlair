package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsdc.PokeAPI.entidades.EvolutionDetailEntity;

public interface EvolutionDetailRepository extends JpaRepository<EvolutionDetailEntity, Long>{
    
}
