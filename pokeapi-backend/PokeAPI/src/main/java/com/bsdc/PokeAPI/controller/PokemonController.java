package com.bsdc.PokeAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsdc.PokeAPI.dto.PokemonDTO;
import com.bsdc.PokeAPI.entidades.PokemonEntity;
import com.bsdc.PokeAPI.model.Pokemon;
import com.bsdc.PokeAPI.services.BDPokeAPIService;
//import com.bsdc.PokeAPI.services.PokemonService;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    private BDPokeAPIService bdPokeAPIService;

    @GetMapping("/id/{id}")
    public ResponseEntity<PokemonDTO> getPokemonById(@PathVariable int id) {
        PokemonEntity pokemon = bdPokeAPIService.getPokemonById(id);
        return ResponseEntity.ok(new PokemonDTO(pokemon));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PokemonDTO> getPokemonByName(@PathVariable String name){
        PokemonEntity pokemon = bdPokeAPIService.getPokemonByName(name);
        return ResponseEntity.ok(new PokemonDTO(pokemon));
    }

    @GetMapping("/allPokemon")
    public ResponseEntity<Page<PokemonDTO>> getAllPokemon(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size){
        return ResponseEntity.ok(bdPokeAPIService.getPokemons(page,size));
    }

    @GetMapping("/porTipo")
    public ResponseEntity<Page<PokemonDTO>> getPokemonByType(@RequestParam String type, @PageableDefault(size=50, sort="id") Pageable pageable){
        return ResponseEntity.ok(bdPokeAPIService.getPokemonByType(type,pageable));
    }

    @PostMapping("/admin/asignarGen")
    public ResponseEntity<String> asignarGeneraciones(){
        bdPokeAPIService.asignarGeneracion();
        return ResponseEntity.ok("Generaciones asignadas con gran satisfacción");
    }

    @GetMapping("/porGeneracion")
    public ResponseEntity<Page<PokemonDTO>> getPokemonByGeneration(@RequestParam int id, @PageableDefault(size=50, sort="id") Pageable pageable){
        return ResponseEntity.ok(bdPokeAPIService.getPokemonByGeneration(id, pageable));
    }

    @GetMapping("/actualizarSprites")
    public ResponseEntity<String> actualizarSprites(){
        bdPokeAPIService.actualizarSprites();
        return ResponseEntity.ok("Sprites actualizados con exito JEJE");
    }

    @GetMapping("/guardarStats")
    public ResponseEntity<String> guardarStats(){
        bdPokeAPIService.guardarStats();
        return ResponseEntity.ok("Stats guardadas con exito JEJE");
    }

    @GetMapping("/agregarDataPokemonSpecies")
    public ResponseEntity<String> agregarDataPokemonSpecies(){
        bdPokeAPIService.agregarDataPokemonSpecies();
        return ResponseEntity.ok("Se han guardado los capture_rate y las description para cada pokemon JEJE");
    }

    @GetMapping("/agregarEvoluciones")
    public ResponseEntity<String> agregarEvoluciones(){
        for(int chainId = 223; chainId<=549; chainId++){
            try{
                bdPokeAPIService.procesarEvolutionChain(chainId);
            }catch(Exception e){
                System.out.println("Error en la cadena: "+chainId+" -> "+ e.getMessage());
            }
        }
        return ResponseEntity.ok("Se han guardado las evoluciones para cada pokemon JEJE");
    }

    // **************************** Llamada directa a la PokeApi ****************************
    /*@Autowired
    private PokemonService pokemonService;

    @GetMapping("/{id}")
    public Pokemon getPokemonById(@PathVariable int id){
        return pokemonService.getPokemonById(id);
    }

    @GetMapping("/name/{name}")
    public Pokemon getPokemonByName(@PathVariable String name){
        return pokemonService.getPokemonByName(name);
    }
    */

}
