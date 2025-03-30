package com.bsdc.PokeAPI.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.bsdc.PokeAPI.entidades.GenerationEntity;
import com.bsdc.PokeAPI.entidades.PokemonEntity;
import com.bsdc.PokeAPI.entidades.PokemonTypeEntity;

import jakarta.persistence.criteria.Join;

public class PokemonSpecification {
    
    public static Specification<PokemonEntity> hasType(String type){
        return(root, query, criteriaBuilder) ->{
            if(type==null)
            return null;
            
            Join<PokemonEntity, PokemonTypeEntity> joinType = root.join("types");
            return criteriaBuilder.equal(joinType.get("name"), type.toLowerCase());
        };
    }

    public static Specification<PokemonEntity> hasGeneration(int id){
        return(root, query, criteriaBuilder) -> {
            if(id==0)
            return null;
            
            Join<PokemonEntity, GenerationEntity> joinGen = root.join("generation");            
            return criteriaBuilder.equal(joinGen.get("id"), id);
        };
    }
    
}
