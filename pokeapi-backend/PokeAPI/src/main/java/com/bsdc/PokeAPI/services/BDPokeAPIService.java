package com.bsdc.PokeAPI.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bsdc.PokeAPI.dto.PokemonDTO;
import com.bsdc.PokeAPI.entidades.PokemonAbilityEntity;
import com.bsdc.PokeAPI.entidades.PokemonEntity;
import com.bsdc.PokeAPI.entidades.PokemonTypeEntity;
import com.bsdc.PokeAPI.entidades.SpriteEntity;
import com.bsdc.PokeAPI.model.Pokemon;
import com.bsdc.PokeAPI.repositories.PokemonAbilityRepository;
import com.bsdc.PokeAPI.repositories.PokemonRepository;
import com.bsdc.PokeAPI.repositories.PokemonTypeRepository;
import com.bsdc.PokeAPI.repositories.SpriteRepository;

@Service
public class BDPokeAPIService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokemonTypeRepository pokemonTypeRepository;

    @Autowired
    private PokemonAbilityRepository pokemonAbilityRepository;

    @Autowired
    private SpriteRepository spriteRepository;

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon/";

    public void buscarYGuardarPokemon(int id){
        String url = POKEAPI_URL + id;

        Pokemon pokeRespuesta = restTemplate.getForObject(url, Pokemon.class);

        if(pokeRespuesta != null){
            PokemonEntity pokemon = new PokemonEntity();
            pokemon.setId(pokeRespuesta.getId());
            pokemon.setName(pokeRespuesta.getName());
            pokemon.setHeight(pokeRespuesta.getHeight());
            pokemon.setWeight(pokeRespuesta.getWeight());
            
            List<PokemonTypeEntity> types = pokeRespuesta.getTypes().stream()
                    .map(type -> {
                        PokemonTypeEntity pokemonType = new PokemonTypeEntity();
                        pokemonType.setName(type.getType().getName());
                        pokemonType.setPokemon(pokemon);
                        return pokemonType;
                    }).toList();
            pokemon.setTypes(types);

            List<PokemonAbilityEntity> abilities = pokeRespuesta.getAbilities().stream()
                    .map(ability -> {
                        PokemonAbilityEntity pokemonAbility = new PokemonAbilityEntity();
                        pokemonAbility.setName(ability.getAbility().getName());
                        pokemonAbility.setPokemon(pokemon);
                        return pokemonAbility;
                    }).toList();
            pokemon.setAbilities(abilities);

            SpriteEntity spriteEntity = new SpriteEntity();
            spriteEntity.setFront_default(pokeRespuesta.getSprites().getFront_default());
            spriteEntity.setFront_shiny(pokeRespuesta.getSprites().getFront_shiny());
            spriteEntity.setPokemon(pokemon);
            pokemon.setSprites(spriteEntity);

            pokemonRepository.save(pokemon);
            
        }
        
    }


    public Page<PokemonDTO> getPokemons(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PokemonEntity> pokemonPage = pokemonRepository.findAll(pageable);        
        return pokemonPage.map(pokemonEntity -> new PokemonDTO(pokemonEntity));
    }

    public PokemonEntity getPokemonById(int id){
        return pokemonRepository.findById(id);
    }

    public PokemonEntity getPokemonByName(String name){
        return pokemonRepository.findByName(name);
    }
    
}
