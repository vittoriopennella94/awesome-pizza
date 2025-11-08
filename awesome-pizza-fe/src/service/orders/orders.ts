import { Injectable } from '@angular/core';
import {Manager} from '../manager/manager';
import {Observable} from 'rxjs';

export class OrderState {
  stateId: number;
  stateName: string;

  constructor() {
    this.stateId = 0;
    this.stateName = '';
  }
}

export class Order {
  orderId: number;
  customerName: string;
  customerSurname: string;
  customerAddress: string;
  customerStreetNumber: string;
  customerAddInfo: string;
  orderState: number;
  createDatetime: Date;
  updateDatetime: Date;

  constructor() {
    this.orderId = 0;
    this.customerName = "";
    this.customerSurname = "";
    this.customerAddress = "";
    this.customerStreetNumber = "";
    this.customerAddInfo = "";
    this.orderState = 0;
    this.createDatetime = new Date();
    this.updateDatetime = new Date();
  }
}


@Injectable({
  providedIn: 'root',
})
export class Orders {
  constructor(private serviceManager: Manager) {
  }

  public getAllStates(){
    return this.serviceManager.get<OrderState[]>("states");
  }

  public getAllOrders(){
    return this.serviceManager.get<Order[]>("orders");
  }
}
