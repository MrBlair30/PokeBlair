import { Component, OnInit } from '@angular/core';
import { PokemonService } from '../../../services/pokemon.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Pokemon } from '../../../model/pokemon.model';

@Component({
  selector: 'app-pokemon-detalles',
  standalone: false,
  templateUrl: './pokemon-detalles.component.html',
  styleUrl: './pokemon-detalles.component.css'
})
export class PokemonDetallesComponent implements OnInit{

  public pokemon!: Pokemon;

  traduccionesTipos: Record<string, string> = {
    fire: "Fuego",
    water: "Agua",
    grass: "Planta",
    electric: "Eléctrico",
    psychic: "Psíquico",
    fighting: "Lucha",
    dark: "Siniestro",
    fairy: "Hada",
    dragon: "Dragón",
    ice: "Hielo",
    poison: "Veneno",
    ground: "Tierra",
    flying: "Volador",
    bug: "Bicho",
    rock: "Roca",
    ghost: "Fantasma",
    steel: "Acero",
    normal: "Normal"
  };

  constructor(private pokemonService: PokemonService, private router: Router, private route: ActivatedRoute){
  }
  ngOnInit(): void {
    this.cargarPokemon();
  }

  public cargarPokemon(){
    const id = +this.route.snapshot.paramMap.get("id")!;
    this.pokemonService.getPokemonById(id).subscribe({
      next: (data) => {
        this.pokemon = data;
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  public mostrarLista(){
    this.router.navigate([""]);
  }

  public traducirTipo(tipo: string): string{
    return this.traduccionesTipos[tipo];
  }
}
