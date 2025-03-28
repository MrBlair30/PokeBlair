import { Component, OnInit } from '@angular/core';
import { Pokemon } from '../../model/pokemon.model';
import { PokemonService } from '../../services/pokemon.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-pokemon-list',
  standalone: false,
  templateUrl: './pokemon-list.component.html',
  styleUrl: './pokemon-list.component.css'
})
export class PokemonListComponent implements OnInit{

  public pokemonList: Pokemon[] = [];
  public pagActual = 0;
  public pagSize = 50;
  public pokemonsTotal = 0;
  public isLoading = false;
  public cols: number = 4;

  constructor(private pokemonService: PokemonService, private breakPointObserver: BreakpointObserver) {}

  ngOnInit(): void {
    this.cargarPokemons();
    this.ajustarGrid();
  }

  public cargarPokemons() {
    this.isLoading = true;
    this.pokemonService.getAllPokemon(this.pagActual, this.pagSize).subscribe({
      next: (data) => {
        this.pokemonList = [...this.pokemonList, ...data.content];
        this.pokemonsTotal = data.totalElements;
        this.isLoading = false;
      },
      error: (err) => {
        console.log(err);
        this.isLoading = false;
      }
    });
  }


  public cargarMas(){
    if(!this.isLoading && this.pagActual < Math.ceil(this.pokemonsTotal / this.pagSize)-1){
      this.pagActual++;
      this.cargarPokemons();
    }
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
        this.cols = 1;
      }else if(resultado.breakpoints[Breakpoints.Small]){
        this.cols = 2;
      }else if(resultado.breakpoints[Breakpoints.Medium]){
        this.cols = 3;
      }else{
        this.cols = 4;
      }
    })
  }

}
