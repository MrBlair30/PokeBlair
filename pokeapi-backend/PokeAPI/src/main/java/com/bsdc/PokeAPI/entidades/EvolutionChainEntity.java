package com.bsdc.PokeAPI.entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class EvolutionChainEntity {
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_pokemon_id")
    private PokemonEntity basePokemon;
    
    @OneToMany(mappedBy = "evolutionChain", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EvolutionDetailEntity> evolutionDetails;
}
