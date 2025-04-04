package com.bsdc.PokeAPI.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsdc.PokeAPI.entidades.EvolutionDetailEntity;

public interface EvolutionDetailRepository extends JpaRepository<EvolutionDetailEntity, Long>{
    List<EvolutionDetailEntity> findByToPokemonId(int id);
    List<EvolutionDetailEntity> findByFromPokemonId(int id);
}
