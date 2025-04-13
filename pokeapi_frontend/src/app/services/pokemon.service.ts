import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pokemon } from '../model/pokemon.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PokemonService {

  private url = "https://pokeblair-backend-production.up.railway.app/api/pokemon";
  //private url = "http://192.168.1.69:8080/api/pokemon";

  constructor(private http: HttpClient) { }

  public getPokemonById(id: number):Observable<Pokemon>{
    return this.http.get<Pokemon>(`${this.url}/id/${id}`);
  }

  public getPokemonByName(name: string):Observable<Pokemon>{
    return this.http.get<Pokemon>(`${this.url}/name/${name}`);
  }

  public getAllPokemon(page:number, size:number):Observable<any>{
    const params = new HttpParams()
    .set('page', page.toString())
    .set('size', size.toString());
    return this.http.get<Pokemon>(`${this.url}/allPokemon`, { params });
  }

  public getPokemonByType(type:string, page:number, size:number):Observable<any>{
    const params = new HttpParams()
    .set('type', type)
    .set('page', page.toString())
    .set('size', size.toString());

    return this.http.get<Pokemon>(`${this.url}/porTipo`, { params });
  }

  public getPokemonByGeneration(id:string, page:number, size:number):Observable<any>{
    const params = new HttpParams()
    .set('id', id)
    .set('page', page.toString())
    .set('size', size.toString());

    return this.http.get<Pokemon>(`${this.url}/porGeneracion`, { params });
  }

}
