/*package com.bsdc.PokeAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bsdc.PokeAPI.model.Pokemon;

@Service
public class PokemonService {
    
    @Autowired
    private RestTemplate restTemplate;

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon/";

    public Pokemon getPokemonById(int id){
        String url = POKEAPI_URL + id;
        return restTemplate.getForObject(url, Pokemon.class);
    }

    public Pokemon getPokemonByName(String name){
        String url = POKEAPI_URL + name;
        return restTemplate.getForObject(url, Pokemon.class);
    }

}*/
