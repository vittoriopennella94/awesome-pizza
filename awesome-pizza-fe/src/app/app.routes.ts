import { Routes } from '@angular/router';
import {Homepage} from '../pages/homepage/homepage';
import {NuovoOrdine} from '../pages/nuovo-ordine/nuovo-ordine';
import {ListaOrdini} from '../pages/lista-ordini/lista-ordini';
import {ListaOrdiniResolver} from '../resolvers/lista-ordini/lista-ordini.resolver';
import {DettaglioOrdineResolver} from "../resolvers/dettaglio-ordine/dettaglio-ordine.resolver";
import {ErroreComponent} from "../pages/errore/errore.component";

export const routes: Routes = [
  { path: '', component: Homepage }, // Home page
  { path: 'nuovo-ordine', component: NuovoOrdine, resolve: {data: DettaglioOrdineResolver}},
  { path: 'lista-ordini', component: ListaOrdini, resolve: {data: ListaOrdiniResolver}},
    { path: 'errore', component: ErroreComponent}
];
