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
  public spriteNormal: boolean = true;

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

  traduccionesStats: Record<string, string> = {
    "hp": "Salud",
    "attack": "Ataque",
    "defense": "Defensa",
    "special-attack": "Ataque Especial",
    "special-defense": "Defensa Especial",
    "speed": "Velocidad"
  };

  habilidadesPokemon: Record<string, string> = {
    "stench": "Hedor",
    "drizzle": "Llovizna",
    "speed-boost": "Impulso",
    "battle-armor": "Armadura Batalla",
    "sturdy": "Robustez",
    "damp": "Humedad",
    "limber": "Flexibilidad",
    "sand-veil": "Velo Arena",
    "static": "Electricidad Estática",
    "volt-absorb": "Absorbe Electricidad",
    "water-absorb": "Absorbe Agua",
    "oblivious": "Despiste",
    "cloud-nine": "Aclimatación",
    "compound-eyes": "Ojo Compuesto",
    "insomnia": "Insomnio",
    "color-change": "Cambio Color",
    "immunity": "Inmunidad",
    "flash-fire": "Absorbe Fuego",
    "shield-dust": "Polvo Escudo",
    "own-tempo": "Ritmo Propio",
    "suction-cups": "Ventosas",
    "intimidate": "Intimidación",
    "shadow-tag": "Sombra Trampa",
    "rough-skin": "Piel Tosca",
    "wonder-guard": "Superguarda",
    "levitate": "Levitación",
    "effect-spore": "Efecto Espora",
    "synchronize": "Sincronía",
    "clear-body": "Cuerpo Puro",
    "natural-cure": "Cura Natural",
    "lightning-rod": "Pararrayos",
    "serene-grace": "Dicha",
    "swift-swim": "Nado Rápido",
    "chlorophyll": "Clorofila",
    "illuminate": "Iluminación",
    "trace": "Calco",
    "huge-power": "Potencia",
    "poison-point": "Punto Tóxico",
    "inner-focus": "Foco Interno",
    "magma-armor": "Armadura Magma",
    "water-veil": "Velo Agua",
    "magnet-pull": "Imán",
    "soundproof": "Insonorizar",
    "rain-dish": "Cura Lluvia",
    "sand-stream": "Chorro Arena",
    "pressure": "Presión",
    "thick-fat": "Sebo",
    "early-bird": "Madrugar",
    "flame-body": "Cuerpo Llama",
    "run-away": "Fuga",
    "keen-eye": "Vista Lince",
    "hyper-cutter": "Corte Fuerte",
    "pickup": "Recogida",
    "truant": "Ausente",
    "hustle": "Entusiasmo",
    "cute-charm": "Gran Encanto",
    "plus": "Más",
    "minus": "Menos",
    "forecast": "Predicción",
    "sticky-hold": "Viscosidad",
    "shed-skin": "Mudar",
    "guts": "Agallas",
    "marvel-scale": "Escama Especial",
    "liquid-ooze": "Viscosidad",
    "overgrow": "Espesura",
    "blaze": "Mar Llamas",
    "torrent": "Torrente",
    "swarm": "Enjambre",
    "rock-head": "Cabeza Roca",
    "drought": "Sequía",
    "arena-trap": "Trampa Arena",
    "vital-spirit": "Espíritu Vital",
    "white-smoke": "Humo Blanco",
    "pure-power": "Energía Pura",
    "shell-armor": "Caparazón",
    "air-lock": "Bucle Aire",
    "tangled-feet": "Tumbos",
    "motor-drive": "Electromotor",
    "rivalry": "Rivalidad",
    "steadfast": "Impasible",
    "snow-cloak": "Manto Níveo",
    "gluttony": "Gula",
    "anger-point": "Irascible",
    "unburden": "Liviano",
    "heatproof": "Ignífugo",
    "simple": "Simple",
    "dry-skin": "Piel Seca",
    "download": "Descarga",
    "iron-fist": "Puño Férreo",
    "poison-heal": "Antídoto",
    "adaptability": "Adaptable",
    "skill-link": "Encadenado",
    "hydration": "Hidratación",
    "solar-power": "Poder Solar",
    "quick-feet": "Pies Rápidos",
    "normalize": "Normalidad",
    "sniper": "Francotirador",
    "magic-guard": "Muro Mágico",
    "no-guard": "Indefenso",
    "stall": "Rezagado",
    "technician": "Experto",
    "leaf-guard": "Defensa Hoja",
    "klutz": "Zoquete",
    "mold-breaker": "Rompemoldes",
    "super-luck": "Afortunado",
    "aftermath": "Resquicio",
    "anticipation": "Anticipación",
    "forewarn": "Alerta",
    "unaware": "Ignorante",
    "tinted-lens": "Cromolente",
    "filter": "Filtro",
    "slow-start": "Inicio Lento",
    "scrappy": "Intrépido",
    "storm-drain": "Colector",
    "ice-body": "Coraza Helada",
    "solid-rock": "Roca Sólida",
    "snow-warning": "Nevada",
    "honey-gather": "Recogemiel",
    "frisk": "Cacheo",
    "reckless": "Audaz",
    "multitype": "Multitipo",
    "flower-gift": "Don Floral",
    "bad-dreams": "Mal Sueño",
    "pickpocket": "Hurto",
    "sheer-force": "Potencia Bruta",
    "contrary": "Respondón",
    "unnerve": "Nerviosismo",
    "defiant": "Competitivo",
    "defeatist": "Flaqueza",
    "cursed-body": "Cuerpo Maldito",
    "healer": "Alma Cura",
    "friend-guard": "Amigo Guardián",
    "weak-armor": "Armadura Frágil",
    "heavy-metal": "Metal Pesado",
    "light-metal": "Metal Liviano",
    "multiscale": "Compensación",
    "toxic-boost": "Ímpetu Tóxico",
    "flare-boost": "Ímpetu Ardiente",
    "harvest": "Cosecha",
    "telepathy": "Telepatía",
    "moody": "Veleta",
    "overcoat": "Superguarda",
    "poison-touch": "Toque Tóxico",
    "regenerator": "Regeneración",
    "big-pecks": "Sacapecho",
    "sand-rush": "Ímpetu Arena",
    "wonder-skin": "Piel Milagro",
    "analytic": "Cálculo Final",
    "illusion": "Ilusión",
    "imposter": "Impostor",
    "infiltrator": "Allanamiento",
    "mummy": "Momia",
    "moxie": "Arrogancia",
    "justified": "Justiciero",
    "rattled": "Cobardía",
    "magic-bounce": "Espejo Mágico",
    "sap-sipper": "Herbívoro",
    "prankster": "Bromista",
    "sand-force": "Poder Arena",
    "iron-barbs": "Púas de Acero",
    "zen-mode": "Modo Zen",
    "victory-star": "Tinovictoria",
    "turboblaze": "Turbollama",
    "teravolt": "Teravoltio",
    "aroma-veil": "Velo Aroma",
    "flower-veil": "Velo Flor",
    "cheek-pouch": "Carrillo",
    "protean": "Mutatipo",
    "fur-coat": "Pelaje Recio",
    "magician": "Prestidigitador",
    "bulletproof": "Antibalas",
    "competitive": "Tenacidad",
    "strong-jaw": "Mandíbula Fuerte",
    "refrigerate": "Piel Helada",
    "sweet-veil": "Velo Dulce",
    "stance-change": "Cambio Táctico",
    "gale-wings": "Alas Vendaval",
    "mega-launcher": "Megadisparador",
    "grass-pelt": "Manto Frondoso",
    "symbiosis": "Simbiosis",
    "tough-claws": "Garras Duras",
    "pixilate": "Piel Feérica",
    "gooey": "Baba",
    "aerilate": "Piel Celeste",
    "parental-bond": "Amor Filial",
    "dark-aura": "Aura Oscura",
    "fairy-aura": "Aura Feérica",
    "aura-break": "Rompeaura",
    "primordial-sea": "Mar del Albor",
    "desolate-land": "Tierra del Ocaso",
    "delta-stream": "Ráfaga Delta",
    "stamina": "Firmeza",
    "wimp-out": "Huida",
    "emergency-exit": "Retirada",
    "water-compaction": "Hidrorrefuerzo",
    "merciless": "Ensañamiento",
    "shields-down": "Escudo Limitado",
    "stakeout": "Vigilante",
    "water-bubble": "Pompa",
    "steelworker": "Acero Templado",
    "berserk": "Cólera",
    "slush-rush": "Quitanieves",
    "long-reach": "Remoto",
    "liquid-voice": "Voz Fluida",
    "triage": "Primer Auxilio",
    "galvanize": "Piel Eléctrica",
    "surge-surfer": "Cola Surf",
    "schooling": "Banco",
    "disguise": "Disfraz",
    "battle-bond": "Fuerte Afecto",
    "power-construct": "Agrupamiento",
    "corrosion": "Corrosión",
    "comatose": "Letargo Perenne",
    "queenly-majesty": "Regia Presencia",
    "innards-out": "Revés",
    "dancer": "Pareja de Baile",
    "battery": "Batería",
    "fluffy": "Peluche",
    "dazzling": "Cuerpo Vívido",
    "soul-heart": "Coránima",
    "tangling-hair": "Rizos Rebeldes",
    "receiver": "Receptor",
    "power-of-alchemy": "Reacción Química",
    "beast-boost": "Ultraimpulso",
    "rks-system": "Sistema Alfa",
    "electric-surge": "Electrogénesis",
    "psychic-surge": "Psicogénesis",
    "misty-surge": "Niebla Mística",
    "grassy-surge": "Herbogénesis",
    "full-metal-body": "Guardia Metálica",
    "shadow-shield": "Guardia Espectro",
    "prism-armor": "Armadura Prisma",
    "neuroforce": "Fuerza Cerebral",
    "intrepid-sword": "Espada Indómita",
    "dauntless-shield": "Escudo Recio",
    "libero": "Líbero",
    "ball-fetch": "Recogebolas",
    "cotton-down": "Pelaje Algodonoso",
    "propeller-tail": "Hélice Caudal",
    "mirror-armor": "Coraza Espejo",
    "gulp-missile": "Tragamisil",
    "stalwart": "Acérrimo",
    "steam-engine": "Máquina de Vapor",
    "punk-rock": "Punk Rock",
    "sand-spit": "Escuparena",
    "ice-scales": "Escama de Hielo",
    "ripen": "Maduración",
    "ice-face": "Cara de Hielo",
    "power-spot": "Poder Álgido",
    "mimicry": "Mimetismo",
    "screen-cleaner": "Antibarrera",
    "steely-spirit": "Puro Metal",
    "perish-body": "Cuerpo Mortal",
    "wandering-spirit": "Alma Errante",
    "gorilla-tactics": "Táctica Simia",
    "neutralizing-gas": "Gas Reactivo",
    "pastel-veil": "Velo Pastel",
    "hunger-switch": "Mutapetito",
    "quick-draw": "Mano Rápida",
    "unseen-fist": "Puño Invisible",
    "curious-medicine": "Medicina Extraña",
    "transistor": "Transistor",
    "dragons-maw": "Mandíbula Dragón",
    "chilling-neigh": "Relincho Blanco",
    "grim-neigh": "Relincho Negro",
    "as-one-glastrier": "Unión Glastrier",
    "as-one-spectrier": "Unión Spectrier",
    "lingering-aroma": "Aroma Persistente",
    "seed-sower": "Siembra",
    "thermal-exchange": "Intercambio Térmico",
    "anger-shell": "Coraza Ira",
    "purifying-salt": "Sal Purificadora",
    "well-baked-body": "Cuerpo Horneado",
    "wind-rider": "Jinete del Viento",
    "guard-dog": "Perro Guardián",
    "rocky-payload": "Portarrocas",
    "wind-power": "Energía Eólica",
    "zero-to-hero": "De Cero a Héroe",
    "commander": "Comandante",
    "electromorphosis": "Electrogénesis",
    "protosynthesis": "Protosíntesis",
    "quark-drive": "Motor Cuántico",
    "good-as-gold": "Oro Puro",
    "vessel-of-ruin": "Vasija de Ruina",
    "sword-of-ruin": "Espada de Ruina",
    "tablets-of-ruin": "Tablilla de Ruina",
    "beads-of-ruin": "Collar de Ruina",
    "orichalcum-pulse": "Pulso de Oricalco",
    "hadron-engine": "Motor de Hadrones",
    "opportunist": "Oportunista",
    "cud-chew": "Rumia",
    "sharpness": "Cortante",
    "supreme-overlord": "Señor Supremo",
    "costar": "Copiloto",
    "toxic-debris": "Escombro Tóxico",
    "armor-tail": "Cola Armadura",
    "earth-eater": "Come Tierra",
    "mycelium-might": "Poder Micelio",
    "minds-eye": "Ojo Mental",
    "supersweet-syrup": "Jarabe Dulce",
    "hospitality": "Hospitalidad",
    "toxic-chain": "Cadena Tóxica",
    "embody-aspect-teal": "Don Verde",
    "embody-aspect-yellow": "Don Amarillo",
    "embody-aspect-pink": "Don Rosa",
    "embody-aspect-blue": "Don Azul",
    "tera-shift": "Teraformación",
    "tera-shell": "Teraescudo",
    "teraform-zero": "Teraformación Cero",
    "poison-puppeteer": "Marioneta Tóxica",
    "mountaineer": "Montañista",
    "wave-rider": "Surcador",
    "skater": "Patinador",
    "thrust": "Empuje",
    "perception": "Percepción",
    "parasitic-waste": "Desperdicio",
    "spirit-call": "Llamada Espiritual"
  };
    
  evolutionItems: Record<string, string> = {
    "fire-stone": "Piedra Fuego",
    "water-stone": "Piedra Agua",
    "thunder-stone": "Piedra Trueno",
    "leaf-stone": "Piedra Hoja",
    "moon-stone": "Piedra Lunar",
    "sun-stone": "Piedra Solar",
    "shiny-stone": "Piedra Día",
    "dusk-stone": "Piedra Noche",
    "dawn-stone": "Piedra Alba",
    "ice-stone": "Piedra Hielo",
    "metal-coat": "Revestimiento Metálico",
    "dragon-scale": "Escama Dragón",
    "up-grade": "Mejora",
    "protector": "Protector",
    "electirizer": "Electrizador",
    "magmarizer": "Magmatizador",
    "prism-scale": "Escama Prisma",
    "deep-sea-tooth": "Colmillo Marino",
    "deep-sea-scale": "Escama Marina",
    "razor-claw": "Garra Afilada",
    "razor-fang": "Colmillo Afilado",
    "reaper-cloth": "Manto Lúgubre",
    "sachet": "Sobre de Aromas",
    "whipped-dream": "Dulce Sueño",
    "oval-stone": "Piedra Ovalada",
    "kings-rock": "Roca del Rey",
    "pearl-string": "Collar de Perlas",
    "galarica-cuff": "Puño Galar",
    "galarica-wreath": "Guirnalda Galar",
    "black-augurite": "Augurita Negra",
    "peat-block": "Bloque de Turba",
    "syrupy-apple": "Manzana Almíbar",
    "tart-apple": "Manzana Ácida",
    "sweet-apple": "Manzana Dulce",
    "cracked-pot": "Tetera Rajada",
    "chipped-pot": "Tetera Astillada",
    "link-cable": "Cable de Enlace",
    "auspicious-armor": "Armadura Propicia",
    "malicious-armor": "Armadura Maléfica",
    "leaders-crest": "Emblema del Líder",
    "dubious-disc": "Disco Extraño"   
  };

  tiemposDelDia: Record <string, string> = {
    "day": "Día",
    "night": "Noche"
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

        this.pokemon.evolutionChain.forEach(evolution => {
          this.pokemonService.getPokemonById(evolution.id).subscribe({
            next: (evo) => {
              evolution.types = evo.types;
            },
            error: (err) => {
              console.log(err);
            }
          });

        })
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  public cargarPokemonEvo(id: number) {
    this.router.navigateByUrl("/", {skipLocationChange: true}).then(() => {
      this.router.navigate(["/pokemon", id]);
    });
  }

  getPokemonSprite(id: number): string {
    const baseUrl = 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork';
    return `${baseUrl}/${id}.png`;
  }

  public mostrarLista(){
    this.router.navigate([""]);
  }

  public mostrarSiguientePokemon(){
    let id = +this.route.snapshot.paramMap.get("id")!;
    let siguientePokemonId = id + 1;
    this.router.navigateByUrl("/", {skipLocationChange: true}).then(() => {
      this.router.navigate(["/pokemon", siguientePokemonId]);
    })
  }

  public mostrarAnteriorPokemon(){
    let id = +this.route.snapshot.paramMap.get("id")!;
    let anteriorPokemonId = id - 1;
    this.router.navigateByUrl("/", {skipLocationChange: true}).then(() => {
      this.router.navigate(["/pokemon", anteriorPokemonId]);
    })
  }

  public traducirTipo(tipo: string): string{
    return this.traduccionesTipos[tipo];
  }

  public traducirStats(stat: string): string{
    return this.traduccionesStats[stat];
  }

  public traducirHabilidades(habilidad: string): string{
    return this.habilidadesPokemon[habilidad];
  }

  public traducirItems(item: string): string{
    return this.evolutionItems[item];
  }

  public traducirDayNight(day: string): string{
    return this.tiemposDelDia[day];
  }

  public alternarNormalShiny(){
    this.spriteNormal = !this.spriteNormal;
  }
}
