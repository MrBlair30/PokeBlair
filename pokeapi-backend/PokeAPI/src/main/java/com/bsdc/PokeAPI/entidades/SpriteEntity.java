package com.bsdc.PokeAPI.entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class SpriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spriteId;

    private String front_default;
    private String front_shiny;

    @OneToOne
    @JoinColumn(name = "pokemon_id")
    private PokemonEntity pokemon;
}
