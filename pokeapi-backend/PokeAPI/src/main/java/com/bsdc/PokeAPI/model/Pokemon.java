package com.bsdc.PokeAPI.model;

import lombok.Data;
import java.util.List;

@Data
public class Pokemon {
    private int id;
    private String name;
    private int height;
    private int weight;
    private List<PokemonType> types;
    private List<PokemonAbility> abilities;
    private Sprite sprites;
    private List<PokemonStat> stats;
    private Species species;

    @Data
    public static class Species{
        private String url;
    }

}
