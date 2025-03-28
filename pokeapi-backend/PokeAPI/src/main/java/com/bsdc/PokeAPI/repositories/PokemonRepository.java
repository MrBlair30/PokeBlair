package com.bsdc.PokeAPI.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bsdc.PokeAPI.entidades.PokemonEntity;

public interface PokemonRepository extends JpaRepository<PokemonEntity, Integer>{
    PokemonEntity findById(int id);
    PokemonEntity findByName(String name);

    Page<PokemonEntity> findAll(Pageable pageable);
}
