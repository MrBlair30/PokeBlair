package com.bsdc.PokeAPI.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class EvolutionDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evolution_chain_id")
    private EvolutionChainEntity evolutionChain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_pokemon_id")
    private PokemonEntity fromPokemon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_pokemon_id")
    private PokemonEntity toPokemon;


    private String triggerType;
    
    private Integer minLevel;
    private Integer minHappiness;
    private String timeOfDay;
    private String itemName;

}
