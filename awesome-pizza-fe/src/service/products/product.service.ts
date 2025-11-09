import { Injectable } from '@angular/core';
import {ManagerService} from "../manager/manager.service";
import {OrderState, Product} from "../orders/order.service";

@Injectable({
  providedIn: 'root',
})
export class ProductService {
    constructor(private serviceManager: ManagerService) {
    }

    public getAllProducts(){
        return this.serviceManager.get<Product[]>("products");
    }

    public getProductById(id: number){
        return this.serviceManager.get<Product>("products/" + id);
    }
}
