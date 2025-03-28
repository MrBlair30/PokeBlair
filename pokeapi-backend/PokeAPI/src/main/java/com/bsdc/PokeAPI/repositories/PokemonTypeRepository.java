package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsdc.PokeAPI.entidades.PokemonTypeEntity;

public interface PokemonTypeRepository extends JpaRepository<PokemonTypeEntity, Long>{
    
}
