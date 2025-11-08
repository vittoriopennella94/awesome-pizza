import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, map, Observable} from 'rxjs';
import {Order, OrderService, OrderState} from "../../service/orders/order.service";
import {CustomResponse} from "../../service/manager/manager.service";

export interface ListaOrdiniData {
  orders: CustomResponse<Order[]>;
  states: CustomResponse<OrderState[]>;
}


@Injectable({ providedIn: 'root' })
export class ListaOrdiniResolver implements Resolve<ListaOrdiniData> {

  constructor(private orderService: OrderService) {}

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
