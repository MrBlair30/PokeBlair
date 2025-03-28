export interface Pokemon {
    id: number;
    name: string;
    height: number;
    weight: number;
    types: string[];
    abilities: string[];
    sprites: Sprite;
  }
  
  export interface Sprite {
    frontDefault: string;
    frontShiny: string;
  }