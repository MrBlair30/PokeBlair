package com.bsdc.PokeAPI.specifications;

import org.springframework.data.jpa.domain.Specification;

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
    
}
