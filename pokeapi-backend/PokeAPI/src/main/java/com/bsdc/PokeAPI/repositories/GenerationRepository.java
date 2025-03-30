package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bsdc.PokeAPI.entidades.GenerationEntity;

public interface GenerationRepository extends JpaRepository<GenerationEntity, Integer>{

    GenerationEntity findById(int id);

    @Modifying
    @Query("UPDATE PokemonEntity p SET p.generation = :generation WHERE p.id BETWEEN :idInicial AND :idFinal")
    void actualizarGeneracionPorRango(int idInicial, int idFinal, GenerationEntity generation);
}
