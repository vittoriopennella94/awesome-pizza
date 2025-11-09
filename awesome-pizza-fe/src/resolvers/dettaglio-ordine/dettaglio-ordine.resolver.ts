import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, map, Observable, of} from 'rxjs';
import {Order, OrderProduct, OrderService, OrderState, Product} from "../../service/orders/order.service";
import {CustomResponse} from "../../service/manager/manager.service";
import {ProductService} from "../../service/products/product.service";

export interface InserisciOrdineData {
    products: CustomResponse<Product[]>;
    states: CustomResponse<OrderState[]>;
}

@Injectable({ providedIn: 'root' })
export class DettaglioOrdineResolver implements Resolve<InserisciOrdineData> {

    constructor(private orderService: OrderService, private productService: ProductService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<InserisciOrdineData> | Promise<InserisciOrdineData>{
        return forkJoin([this.productService.getAllProducts(), this.orderService.getAllStates()]).pipe(map(([products, states]) => ({
            products: products || [],
            states: states || [],
        })))
    }
}
