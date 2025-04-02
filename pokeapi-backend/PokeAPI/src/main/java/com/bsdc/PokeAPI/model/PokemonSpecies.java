package com.bsdc.PokeAPI.model;

import java.util.List;

import lombok.Data;

@Data
public class PokemonSpecies {
    private int capture_rate;

    private List<FlavorTextEntries> flavor_text_entries;
    private EvolutionChain evolution_chain;

    @Data
    public static class FlavorTextEntries{
        private String flavor_text;
        private Language language;
    }

    @Data
    public static class Language{
        private String name;
    }

    @Data
    public static class EvolutionChain{
        private String url;
    }
}
