package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bsdc.PokeAPI.entidades.SpriteEntity;

public interface SpriteRepository extends JpaRepository<SpriteEntity, Long>{
    @Modifying
    @Query("UPDATE SpriteEntity s SET s.front_default = :frontDefault, s.front_shiny = :frontShiny WHERE s.pokemon.id = :pokemonId")
    public void actualizarSprites(
        @Param("pokemonId") int pokemonId,
        @Param("frontDefault") String frontDefault,
        @Param("frontShiny") String frontShiny
        );
}
