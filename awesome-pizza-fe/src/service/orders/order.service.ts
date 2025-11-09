import { Injectable } from '@angular/core';
import {ManagerService} from "../manager/manager.service";

export class OrderState {
    stateId: number;
    stateName: string;

    constructor() {
        this.stateId = 0;
        this.stateName = '';
    }
}

export class OrderProduct {
    product: Product;
    quantity: number;
    note?: string;

    constructor() {
        this.product = new Product();
        this.quantity = 0;
        this.note = "";
    }
}

export class Product {
    productName: string;
    productDescription: string;
    productPrice: number;

    constructor() {
        this.productName = '';
        this.productDescription = '';
        this.productPrice = 0.0;
    }
}

export class Order {
    orderId: number;
    customerName: string;
    customerSurname: string;
    customerAddress: string;
    customerStreetNumber: string;
    customerAddInfo: string;
    orderState: string;
    products?: OrderProduct[];

    constructor() {
        this.orderId = 0;
        this.customerName = "";
        this.customerSurname = "";
        this.customerAddress = "";
        this.customerStreetNumber = "";
        this.customerAddInfo = "";
        this.orderState = "";
        this.products = [];
    }
}


@Injectable({
    providedIn: 'root',
})
export class OrderService {
    constructor(private serviceManager: ManagerService) {
    }

    public getAllStates(){
        return this.serviceManager.get<OrderState[]>("states");
    }

    public getAllOrders(){
        return this.serviceManager.get<Order[]>("orders");
    }

    public getOrderDetails(id: number){
        return this.serviceManager.get<Order>("orders/" + id);
    }
}
