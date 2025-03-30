package com.bsdc.PokeAPI.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class GenerationEntity {
    
    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "generation")    
    private List<PokemonEntity> pokemons;    

}
