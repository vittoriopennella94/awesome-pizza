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
    orderId: number;
    productId: number;
    productName: string;
    quantity: number;
    note?: string;

    constructor() {
        this.orderId = 0;
        this.productId = 0;
        this.quantity = 0;
        this.productName = "";
        this.note = "";
    }
}

export class Product {
    productId: number;
    productName: string;
    productDescription: string;
    productPrice: number;

    constructor() {
        this.productId = 0;
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

    constructor() {
        this.orderId = 0;
        this.customerName = "";
        this.customerSurname = "";
        this.customerAddress = "";
        this.customerStreetNumber = "";
        this.customerAddInfo = "";
        this.orderState = "";
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
        return this.serviceManager.get<OrderProduct[]>("orders/" + id + "/details");
    }
}
