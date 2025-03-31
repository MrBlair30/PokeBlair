package com.bsdc.PokeAPI.model;

import lombok.Data;

@Data
public class PokemonStat {
    private int base_stat;

    private Stat stat;

    @Data
    public static class Stat {
        private String name;
    }
}
