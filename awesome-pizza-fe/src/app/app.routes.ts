import { Routes } from '@angular/router';
import {Homepage} from '../pages/homepage/homepage';
import {NuovoOrdine} from '../pages/nuovo-ordine/nuovo-ordine';
import {ListaOrdini} from '../pages/lista-ordini/lista-ordini';
import {ListaOrdiniResolver} from '../resolvers/lista-ordini/lista-ordini.resolver';
import {DettaglioOrdineComponent} from "../pages/dettaglio-ordine/dettaglio-ordine.component";
import {DettaglioOrdineResolver} from "../resolvers/dettaglio-ordine/dettaglio-ordine.resolver";

export const routes: Routes = [
  { path: '', component: Homepage }, // Home page
  { path: 'nuovo-ordine', component: NuovoOrdine },
  { path: 'lista-ordini', component: ListaOrdini, resolve: {data: ListaOrdiniResolver}},
    { path: 'dettaglio-ordine/:id', component: DettaglioOrdineComponent, resolve: {data: DettaglioOrdineResolver}},
];
