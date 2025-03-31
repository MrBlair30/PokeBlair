import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PokemonListComponent } from './components/pokemon-list/pokemon-list.component';
import { PokemonDetallesComponent } from './components/pokemon-detalles/pokemon-detalles/pokemon-detalles.component';

const routes: Routes = [
  {path: "pokemon-list",component: PokemonListComponent},
  {path: "pokemon/:id",component: PokemonDetallesComponent},
  {path: "",redirectTo: "pokemon-list",pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
