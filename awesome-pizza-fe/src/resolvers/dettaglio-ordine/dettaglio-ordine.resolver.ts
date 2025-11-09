import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, map, Observable, of} from 'rxjs';
import {Order, OrderService, OrderState} from "../../service/orders/order.service";
import {CustomResponse} from "../../service/manager/manager.service";


@Injectable({ providedIn: 'root' })
export class DettaglioOrdineResolver implements Resolve<CustomResponse<Order>> {

  constructor(private orderService: OrderService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<CustomResponse<Order>> | Promise<CustomResponse<Order>>{
      let id = 0;

      if(route && route.paramMap && route.paramMap.has("id")){
          id = route.paramMap.get("id") !== '' ? Number(route.paramMap.get("id")) : 0;
      }

      return this.orderService.getOrderDetails(id);
  }
}
