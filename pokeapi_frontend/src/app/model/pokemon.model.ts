export interface Pokemon {
    id: number;
    name: string;
    height: number;
    weight: number;
    types: string[];
    abilities: string[];
    sprites: Sprite;
    generation: Generation;
    description: string;
    capture_rate: number;
    stats: Stat[];
    evolutionChain: EvolutionChain[];
  }
  
  export interface Sprite {
    frontDefault: string;
    frontShiny: string;
  }

  export interface Generation {
    id: number;
    name: string;
  }

  export interface Stat {
    name: string;
    base_stat: number;
  }

  export interface EvolutionChain {
    id: number;
    name: string;
    triggerType: string;
    minLevel: number;
    itemName: string;
    timeOfDay: string;
    types: string[];
  }