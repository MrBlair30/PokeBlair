package com.bsdc.PokeAPI.model;

import java.util.List;

import lombok.Data;

@Data
public class PokemonEvolution {
    private Chain chain;

    @Data
    public static class Chain{
        private Species species;
        private List<EvolutionDetail> evolution_details;
        private List<EvolvesTo> evolves_to;
    }

    @Data
    public static class Species{
        private String name;
        private String url;
    }

    @Data
    public static class EvolutionDetail{        
        private Integer min_level;
        private String time_of_day;
        private Integer min_happiness;
        private NamedResource item;
        private NamedResource trigger;
    }

    @Data
    public static class EvolvesTo{
        private Species species;
        private List<EvolutionDetail> evolution_details;
        private List<EvolvesTo> evolves_to;
    }

    @Data
    public static class NamedResource{
        private String name;
    }
}
