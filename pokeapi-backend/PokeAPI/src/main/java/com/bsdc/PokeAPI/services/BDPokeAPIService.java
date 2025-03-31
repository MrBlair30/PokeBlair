package com.bsdc.PokeAPI.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bsdc.PokeAPI.dto.PokemonDTO;
import com.bsdc.PokeAPI.entidades.GenerationEntity;
import com.bsdc.PokeAPI.entidades.PokemonAbilityEntity;
import com.bsdc.PokeAPI.entidades.PokemonEntity;
import com.bsdc.PokeAPI.entidades.PokemonTypeEntity;
import com.bsdc.PokeAPI.entidades.SpriteEntity;
import com.bsdc.PokeAPI.model.Pokemon;
import com.bsdc.PokeAPI.repositories.GenerationRepository;
import com.bsdc.PokeAPI.repositories.PokemonRepository;
import com.bsdc.PokeAPI.repositories.SpriteRepository;
import com.bsdc.PokeAPI.specifications.PokemonSpecification;

@Service
public class BDPokeAPIService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private SpriteRepository spriteRepository;

    @Autowired
    private GenerationRepository generationRepository;


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
            spriteEntity.setFront_default(pokeRespuesta.getSprites().getOther().getOfficialArtwork().getFront_default());
            spriteEntity.setFront_shiny(pokeRespuesta.getSprites().getOther().getOfficialArtwork().getFront_shiny());
            spriteEntity.setPokemon(pokemon);
            pokemon.setSprites(spriteEntity);

            pokemonRepository.save(pokemon);
            
        }
        
    }

    @Transactional
    public void asignarGeneracion() {
        Map<Integer, Map.Entry<Integer, Integer>> generaciones = Map.of(
            1, Map.entry(1, 151),
            2, Map.entry(152, 251),
            3, Map.entry(252, 386),
            4, Map.entry(387, 493),
            5, Map.entry(494, 649),
            6, Map.entry(650, 721),
            7, Map.entry(722, 809),
            8, Map.entry(810, 905),
            9, Map.entry(906, 1025)
        );

        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> entry : generaciones.entrySet()) {
            int generacionId = entry.getKey();
            int min = entry.getValue().getKey();
            int max = entry.getValue().getValue();

            GenerationEntity generacion = generationRepository.findById(generacionId);
            
            generationRepository.actualizarGeneracionPorRango(min, max, generacion);
        }
    }


    @Transactional
    public void actualizarSprites(){
        for(int id = 1; id<=1025; id++){
            String url = POKEAPI_URL + id;
            Pokemon pokeRespuesta = restTemplate.getForObject(url, Pokemon.class);

            String nuevoFrontDefault = pokeRespuesta.getSprites().getOther().getOfficialArtwork().getFront_default();
            String nuevoFrontShiny = pokeRespuesta.getSprites().getOther().getOfficialArtwork().getFront_shiny();

            spriteRepository.actualizarSprites(id, nuevoFrontDefault, nuevoFrontShiny);
        }
    }


    public Page<PokemonDTO> getPokemons(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<PokemonEntity> pokemonPage = pokemonRepository.findAll(pageable);        
        return pokemonPage.map(pokemonEntity -> new PokemonDTO(pokemonEntity));
    }

    public PokemonEntity getPokemonById(int id){
        return pokemonRepository.findById(id);
    }

    public PokemonEntity getPokemonByName(String name){
        return pokemonRepository.findByName(name);
    }

    public Page<PokemonDTO> getPokemonByType(String type, Pageable pageable){
        return pokemonRepository.findAll(PokemonSpecification.hasType(type), pageable)
        .map(pokemonEntity -> new PokemonDTO(pokemonEntity));
    }
    

    public Page<PokemonDTO> getPokemonByGeneration(int id, Pageable pageable){
        return pokemonRepository.findAll(PokemonSpecification.hasGeneration(id), pageable)
        .map(pokemonEntity -> new PokemonDTO(pokemonEntity));
    }

}
