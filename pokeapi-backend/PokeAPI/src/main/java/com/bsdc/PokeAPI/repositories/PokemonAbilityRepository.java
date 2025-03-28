package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsdc.PokeAPI.entidades.PokemonAbilityEntity;

public interface PokemonAbilityRepository extends JpaRepository<PokemonAbilityEntity, Long>{
    
}
