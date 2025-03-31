package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bsdc.PokeAPI.entidades.PokemonStatEntity;

public interface PokemonStatRepository extends JpaRepository<PokemonStatEntity, Long>{
   
}
