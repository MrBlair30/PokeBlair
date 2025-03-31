import { Component, OnInit } from '@angular/core';
import { Pokemon } from '../../model/pokemon.model';
import { PokemonService } from '../../services/pokemon.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pokemon-list',
  standalone: false,
  templateUrl: './pokemon-list.component.html',
  styleUrl: './pokemon-list.component.css'
})
export class PokemonListComponent implements OnInit{

  public pokemonList: Pokemon[] = [];
  public pagActual = 0;
  public pagSize = 0;
  public pokemonsTotal = 0;
  public isLoading = false;
  public cargandoNuevos = false;
  public cols: number = 4;

  public types: string[] = 
  [
    "fire","water","grass","electric","steel","fairy","dark","bug","psychic","ground","rock","flying",
    "dragon","poison","normal","ghost","fighting","ice"    
  ];

  public generations: string[] = ["1","2","3","4","5","6","7","8","9"];

  public busqueda: string = "";

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

  public tipoSeleccionado: string = "";
  public generacionSeleccionada: string = "";

  constructor(private pokemonService: PokemonService, private breakPointObserver: BreakpointObserver, private router: Router) {}

  ngOnInit(): void {
    this.ajustarGrid();
    this.cargarPokemons();
  }

  public cargarPokemons() {
    this.isLoading = true;
    if(this.tipoSeleccionado=="" && this.generacionSeleccionada==""){
      this.pokemonService.getAllPokemon(this.pagActual, this.pagSize).subscribe({
        next: (data) => {
          this.pokemonList = [...this.pokemonList, ...data.content];
          this.pokemonsTotal = data.totalElements;
          this.isLoading = false;
          this.cargandoNuevos = false;
        },
        error: (err) => {
          console.log(err);
          this.isLoading = false;
          this.cargandoNuevos = false;
        }
      });
      return;
    }else if(this.tipoSeleccionado!=""){
      this.pokemonService.getPokemonByType(this.tipoSeleccionado, this.pagActual, this.pagSize).subscribe({
        next: (data) => {
          this.pokemonList = [...this.pokemonList, ...data.content];
          this.pokemonsTotal = data.totalElements;
          this.isLoading = false;
          this.cargandoNuevos = false;
        },
        error: (err) => {
          console.log(err);
          this.isLoading = false;
          this.cargandoNuevos = false;
        }
      });
      return;
    }else if(this.generacionSeleccionada!=""){
      this.pokemonService.getPokemonByGeneration(this.generacionSeleccionada,this.pagActual, this.pagSize).subscribe({
        next: (data) => {
          this.pokemonList = [...this.pokemonList,...data.content];
          this.pokemonsTotal = data.totalElements;
          this.isLoading = false;
          this.cargandoNuevos = false;
        },
        error: (err) => {
          console.log(err);
          this.isLoading = false;
          this.cargandoNuevos = false;
        }
      });
      return;
    }
    
  }


  public cargarMas(){
    if(!this.isLoading && this.pagActual < Math.ceil(this.pokemonsTotal / this.pagSize)-1){
      this.pagActual++;
      this.cargarPokemons();
    }
  }

  public cambioDeTipo(){
    this.generacionSeleccionada = "";
    this.pagActual = 0;
    this.pokemonList = [];
    this.cargandoNuevos = true;
    this.cargarPokemons();   
  }
  
  public cambioDeGeneracion(){
    this.tipoSeleccionado = "";
    this.pagActual = 0;
    this.pokemonList = [];
    this.cargandoNuevos = true;
    this.cargarPokemons();    
  }
  
  onScroll(event: any) {
    const element = event.target;
    const atBottom = element.scrollHeight - element.scrollTop <= element.clientHeight + 100;
    if (atBottom && !this.isLoading) {
      this.cargarMas();
    }
  }

  public ajustarGrid(){
    this.breakPointObserver.observe([
      Breakpoints.XSmall,
      Breakpoints.Small,
      Breakpoints.Medium,
      Breakpoints.Large
    ]).subscribe(resultado =>{
      if(resultado.breakpoints[Breakpoints.XSmall]){
        this.pagSize = 5;
        this.cols = 1;
      }else if(resultado.breakpoints[Breakpoints.Small]){
        this.pagSize = 10;
        this.cols = 2;
      }else if(resultado.breakpoints[Breakpoints.Medium]){
        this.pagSize = 20;
        this.cols = 3;
      }else{
        this.pagSize = 50;
        this.cols = 4;
      }
    })
  }

  public traducirTipo(tipo: string): string{
    return this.traduccionesTipos[tipo];
  }

  public mostrarDetalles(pokemonId: number){    
    this.router.navigate(["/pokemon",pokemonId]);
  }


  public buscarPokemon(){
    alert("Buscando...");
    if(!this.busqueda.trim()){
      return;
    }

    if(!isNaN(Number(this.busqueda.trim()))){
      this.router.navigate(["/pokemon", this.busqueda.trim()]);
    }else{
      this.pokemonService.getPokemonByName(this.busqueda.trim().toLowerCase()).subscribe({
        next: (data) => {
          this.router.navigate(["/pokemon", data.id]);
        },
        error: (err) => {
          console.log(err);
        }
      })
    }
  }

}
