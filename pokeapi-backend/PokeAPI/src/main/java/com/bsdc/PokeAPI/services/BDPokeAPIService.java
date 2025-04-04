package com.bsdc.PokeAPI.services;

import java.util.ArrayList;
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
import com.bsdc.PokeAPI.dto.PokemonDTO.EvolutionChainDTO;
import com.bsdc.PokeAPI.entidades.EvolutionChainEntity;
import com.bsdc.PokeAPI.entidades.EvolutionDetailEntity;
import com.bsdc.PokeAPI.entidades.GenerationEntity;
import com.bsdc.PokeAPI.entidades.PokemonAbilityEntity;
import com.bsdc.PokeAPI.entidades.PokemonEntity;
import com.bsdc.PokeAPI.entidades.PokemonStatEntity;
import com.bsdc.PokeAPI.entidades.PokemonTypeEntity;
import com.bsdc.PokeAPI.entidades.SpriteEntity;
import com.bsdc.PokeAPI.model.Pokemon;
import com.bsdc.PokeAPI.model.PokemonEvolution;
import com.bsdc.PokeAPI.model.PokemonSpecies;
import com.bsdc.PokeAPI.repositories.EvolutionChainRepository;
import com.bsdc.PokeAPI.repositories.EvolutionDetailRepository;
import com.bsdc.PokeAPI.repositories.GenerationRepository;
import com.bsdc.PokeAPI.repositories.PokemonRepository;
import com.bsdc.PokeAPI.repositories.PokemonStatRepository;
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

    @Autowired
    private PokemonStatRepository pokemonStatRepository;

    @Autowired
    private EvolutionChainRepository evolutionChainRepository;

    @Autowired
    private EvolutionDetailRepository evolutionDetailRepository;


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


    @Transactional
    public void guardarStats(){
        for(int id = 1; id<=1025; id++){
            String url = POKEAPI_URL + id;

            Pokemon pokeRespuesta = restTemplate.getForObject(url, Pokemon.class);

            PokemonEntity pokemon = pokemonRepository.findById(id);
            List<PokemonStatEntity> stats = pokeRespuesta.getStats().stream()
            .map(stat -> {
                PokemonStatEntity pokemonStat = new PokemonStatEntity();
                pokemonStat.setBase_stat(stat.getBase_stat());
                pokemonStat.setName(stat.getStat().getName());
                pokemonStat.setPokemon(pokemon); //Establece el pokemon del PokemonStatEntity, por ejemplo bulbasaur
                return pokemonStat;
            }).toList();

            pokemon.setStats(stats); //Establece las stats del pokemon actual, por ejemplo bulbasaur

            pokemonStatRepository.saveAll(stats);
        }
    }

    
    @Transactional
    public void agregarDataPokemonSpecies(){
        for(int id = 1; id<=1025; id++){
            String url = POKEAPI_URL + id;
            Pokemon pokeRespuesta = restTemplate.getForObject(url, Pokemon.class);

            String url2 = pokeRespuesta.getSpecies().getUrl();

            PokemonSpecies pokeRespuesta2 = restTemplate.getForObject(url2, PokemonSpecies.class);

            PokemonEntity pokemon = pokemonRepository.findById(id);

            pokemon.setCapture_rate(pokeRespuesta2.getCapture_rate());

            pokemon.setDescription(pokeRespuesta2.getFlavor_text_entries().stream()
            .filter(text -> text.getLanguage().getName().equals("es"))
            .map(desc -> desc.getFlavor_text().replace("\n"," "))
            .findFirst()
            .orElseGet(() -> {
                return pokeRespuesta2.getFlavor_text_entries().stream()
                .filter(text -> text.getLanguage().getName().equals("en"))
                .map(desc -> desc.getFlavor_text().replace("\n"," "))
                .findFirst()
                .orElse("Descripción no disponible :(");
            })
            );

            pokemonRepository.save(pokemon);
        }
    }

    // CADENAS DE EVOLUCIONES
    @Transactional
    public void procesarEvolutionChain(int chainId){
        String url = "https://pokeapi.co/api/v2/evolution-chain/" + chainId;        
        PokemonEvolution pokeRespuesta = restTemplate.getForObject(url, PokemonEvolution.class);

        PokemonEntity basePokemon = pokemonRepository.findByName(pokeRespuesta.getChain().getSpecies().getName());
        
        EvolutionChainEntity chain = new EvolutionChainEntity();
        chain.setId(chainId);
        chain.setBasePokemon(basePokemon);
        evolutionChainRepository.save(chain);

        procesarChainRecursivo(pokeRespuesta.getChain(), chain, null);
    }

    public void procesarChainRecursivo(PokemonEvolution.Chain chain, EvolutionChainEntity chainEntity, PokemonEntity fromPokemon){

        PokemonEntity pokemonActual = pokemonRepository.findByName(chain.getSpecies().getName());

        if(fromPokemon != null){
            EvolutionDetailEntity detail = new EvolutionDetailEntity();
            detail.setEvolutionChain(chainEntity);
            detail.setFromPokemon(fromPokemon);
            detail.setToPokemon(pokemonActual);
        

            if(!chain.getEvolution_details().isEmpty()){
                detail.setTriggerType(chain.getEvolution_details().get(0).getTrigger().getName());
                detail.setItemName(chain.getEvolution_details().get(0).getItem() !=null ? chain.getEvolution_details().get(0).getItem().getName() : null);
                detail.setMinLevel(chain.getEvolution_details().get(0).getMin_level());
                detail.setMinHappiness(chain.getEvolution_details().get(0).getMin_happiness());
                detail.setTimeOfDay(chain.getEvolution_details().get(0).getTime_of_day());
            }

            evolutionDetailRepository.save(detail);
        }


        for(PokemonEvolution.EvolvesTo evolvesTo: chain.getEvolves_to()){
            procesarEvoluciones(evolvesTo, chainEntity, pokemonActual);
        }
    }


    public void procesarEvoluciones(PokemonEvolution.EvolvesTo evolvesTo, EvolutionChainEntity chainEntity, PokemonEntity fromPokemon){
        PokemonEntity toPokemon = pokemonRepository.findByName(evolvesTo.getSpecies().getName());

        if(fromPokemon != null){
            EvolutionDetailEntity detail = new EvolutionDetailEntity();
            detail.setEvolutionChain(chainEntity);
            detail.setFromPokemon(fromPokemon);
            detail.setToPokemon(toPokemon);

            if(!evolvesTo.getEvolution_details().isEmpty()){
                detail.setTriggerType(evolvesTo.getEvolution_details().get(0).getTrigger().getName());
                detail.setItemName(evolvesTo.getEvolution_details().get(0).getItem() !=null ? evolvesTo.getEvolution_details().get(0).getItem().getName() : null);
                detail.setMinLevel(evolvesTo.getEvolution_details().get(0).getMin_level());
                detail.setMinHappiness(evolvesTo.getEvolution_details().get(0).getMin_happiness());
                detail.setTimeOfDay(evolvesTo.getEvolution_details().get(0).getTime_of_day());
            }

            evolutionDetailRepository.save(detail);
        }

        for(PokemonEvolution.EvolvesTo nextEvol: evolvesTo.getEvolves_to()){
            procesarEvoluciones(nextEvol, chainEntity, toPokemon);
        }
    }


    public PokemonDTO getPokemonConEvoluciones(int id){
        PokemonEntity pokemon = pokemonRepository.findById(id);
        
        PokemonDTO dto = new PokemonDTO(pokemon);
        
        PokemonEntity chainStart = econtrarInicioCadena(pokemon);        
        dto.setEvolutionChain(getCadenaCompleta(chainStart));
        
        return dto;
    }

    public PokemonEntity econtrarInicioCadena(PokemonEntity pokemon){
        return evolutionDetailRepository.findByToPokemonId(pokemon.getId()).
        stream()
        .findFirst()
        .map(EvolutionDetailEntity::getFromPokemon)
        .map(this::econtrarInicioCadena)
        .orElse(pokemon);
    }

    public List<PokemonDTO.EvolutionChainDTO> getCadenaCompleta(PokemonEntity pokemon) {
        return buildChainRecursive(pokemon, null);
    }
    
    private List<PokemonDTO.EvolutionChainDTO> buildChainRecursive(PokemonEntity pokemon, EvolutionDetailEntity evolutionDetail) {
        List<PokemonDTO.EvolutionChainDTO> chain = new ArrayList<>();
        
        // Añadir el Pokémon actual con sus detalles de evolución
        chain.add(new PokemonDTO.EvolutionChainDTO(
            pokemon.getId(),
            pokemon.getName(),
            evolutionDetail != null ? evolutionDetail.getTriggerType() : null,
            evolutionDetail != null ? evolutionDetail.getMinLevel() : null,
            evolutionDetail != null ? evolutionDetail.getItemName() : null,
            evolutionDetail != null ? evolutionDetail.getTimeOfDay() : null
        ));
    
        // Procesar evoluciones siguientes
        evolutionDetailRepository.findByFromPokemonId(pokemon.getId())
            .forEach(evo -> {
                chain.addAll(buildChainRecursive(evo.getToPokemon(), evo));
            });
        
        return chain;
    }
}
