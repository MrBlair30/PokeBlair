package com.bsdc.PokeAPI.entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PokemonAbilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long abilityId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "pokemon_id")
    private PokemonEntity pokemon;
}
