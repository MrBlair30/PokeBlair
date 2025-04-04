package com.bsdc.PokeAPI.dto;

import com.bsdc.PokeAPI.entidades.GenerationEntity;
import com.bsdc.PokeAPI.entidades.PokemonAbilityEntity;
import com.bsdc.PokeAPI.entidades.PokemonEntity;
import com.bsdc.PokeAPI.entidades.PokemonStatEntity;
import com.bsdc.PokeAPI.entidades.PokemonTypeEntity;
import com.bsdc.PokeAPI.entidades.SpriteEntity;
import lombok.Data;

import java.util.List;

@Data
public class PokemonDTO {
    private int id;
    private String name;
    private int height;
    private int weight;
    private Integer capture_rate;
    private String description;
    private List<String> types;       
    private List<String> abilities;   
    private SpriteDTO sprites;     
    private GenerationDTO generation;   
    private List<StatDTO> stats;
    private List<EvolutionChainDTO> evolutionChain;
    
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

        this.stats = pokemon.getStats().stream()
            .map(stat -> new StatDTO(stat)).toList();

        this.capture_rate = pokemon.getCapture_rate();
        this.description = pokemon.getDescription();

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

    @Data
    public static class StatDTO{
        private int base_stat;
        private String name;

        public StatDTO(PokemonStatEntity stat){
            this.base_stat = stat.getBase_stat();
            this.name = stat.getName();
        }
    }

    @Data
    public static class EvolutionChainDTO{
        private int id;
        private String name;
        private String triggerType;
        private Integer minLevel;
        private String itemName;
        private String timeOfDay;

        public EvolutionChainDTO(int id, String name, String triggerType, Integer minLevel, String itemName, String timeOfDay){
            this.id = id;
            this.name = name;
            this.triggerType = triggerType;
            this.minLevel = minLevel;
            this.itemName = itemName;
            this.timeOfDay = timeOfDay;
        }
    }
}