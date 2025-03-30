export interface Pokemon {
    id: number;
    name: string;
    height: number;
    weight: number;
    types: string[];
    abilities: string[];
    sprites: Sprite;
    generation: Generation
  }
  
  export interface Sprite {
    frontDefault: string;
    frontShiny: string;
  }

  export interface Generation {
    id: number;
    name: string;
  }