package com.bsdc.PokeAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsdc.PokeAPI.entidades.EvolutionChainEntity;

public interface EvolutionChainRepository extends JpaRepository<EvolutionChainEntity, Integer>{
    
}
