package com.bsdc.PokeAPI.dto;

import com.bsdc.PokeAPI.entidades.GenerationEntity;
import com.bsdc.PokeAPI.entidades.PokemonAbilityEntity;
import com.bsdc.PokeAPI.entidades.PokemonEntity;
import com.bsdc.PokeAPI.entidades.PokemonTypeEntity;
import com.bsdc.PokeAPI.entidades.SpriteEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PokemonDTO {
    private int id;
    private String name;
    private int height;
    private int weight;
    private List<String> types;       
    private List<String> abilities;   
    private SpriteDTO sprites;     
    private GenerationDTO generation;   

    
    public PokemonDTO(PokemonEntity pokemon) {
        this.id = pokemon.getId();
        this.name = pokemon.getName();
        this.height = pokemon.getHeight();
        this.weight = pokemon.getWeight();
        
        this.types = pokemon.getTypes().stream()
                .map(PokemonTypeEntity::getName)
                .toList();
        
        this.abilities = pokemon.getAbilities().stream()
                .map(PokemonAbilityEntity::getName)
                .toList();
        
        this.sprites = new SpriteDTO(pokemon.getSprites());
        this.generation = new GenerationDTO(pokemon.getGeneration());
    }
    
    @Data
    public static class SpriteDTO {
        private String frontDefault;
        private String frontShiny;

        public SpriteDTO(SpriteEntity sprite) {
            this.frontDefault = sprite.getFront_default();
            this.frontShiny = sprite.getFront_shiny();
        }
    }

    @Data
    public static class GenerationDTO{
        private int id;
        private String name;

        public GenerationDTO(GenerationEntity generation){
            this.id = generation.getId();
            this.name = generation.getName();
        }
    }
}