import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, map, Observable} from 'rxjs';
import {Order, Orders, OrderState} from '../../service/orders/orders';
import {CustomResponse} from '../../service/manager/manager';

export interface ListaOrdiniData {
  orders: CustomResponse<Order[]>;
  states: CustomResponse<OrderState[]>;
}


@Injectable({ providedIn: 'root' })
export class ListaOrdiniResolver implements Resolve<ListaOrdiniData> {

  constructor(private orderService: Orders) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<ListaOrdiniData> | Promise<ListaOrdiniData>{
    return forkJoin([this.orderService.getAllOrders(), this.orderService.getAllStates()]).pipe(map(([orders, states]) => ({
      orders: orders || [],
      states: states || [],
    })))
  }
}
