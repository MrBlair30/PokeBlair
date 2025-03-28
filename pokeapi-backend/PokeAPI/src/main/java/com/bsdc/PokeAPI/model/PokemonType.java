package com.bsdc.PokeAPI.model;

import lombok.Data;

@Data
public class PokemonType {
    private Type type;
    
    @Data
    public static class Type {
        private String name;
    }
}
