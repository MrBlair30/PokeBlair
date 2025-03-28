package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsdc.PokeAPI.entidades.SpriteEntity;

public interface SpriteRepository extends JpaRepository<SpriteEntity, Long>{
    
}
