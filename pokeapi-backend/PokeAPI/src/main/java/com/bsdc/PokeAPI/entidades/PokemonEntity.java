package com.bsdc.PokeAPI.entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class PokemonEntity {
    
    @Id
    private int id;

    private String name;

    private int height;

    private int weight;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PokemonTypeEntity> types;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PokemonAbilityEntity> abilities;

    @OneToOne(mappedBy = "pokemon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SpriteEntity sprites;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id")
    private GenerationEntity generation;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PokemonStatEntity> stats;

}
