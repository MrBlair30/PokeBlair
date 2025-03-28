package com.bsdc.PokeAPI.model;

import lombok.Data;

@Data
public class PokemonAbility {
    private Ability ability;
    
    @Data
    public static class Ability {
        private String name;
    }
}