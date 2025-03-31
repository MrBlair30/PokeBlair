package com.bsdc.PokeAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Sprite {
    private Other other;


    @Data 
    public static class Other{
        @JsonProperty("official-artwork")
        private OfficialArtwork officialArtwork;
    }
    @Data 
    public static class OfficialArtwork{
        private String front_default;
        private String front_shiny;
    }
}
