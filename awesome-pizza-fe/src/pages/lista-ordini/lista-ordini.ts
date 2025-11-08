import {Component, OnInit} from '@angular/core';
import {MatTableModule} from '@angular/material/table';
import {Order, Orders, OrderState} from '../../service/orders/orders';
import {ActivatedRoute} from '@angular/router';


export interface OrderData {
  orderId: number;
  orderNumber: number;
  customerName: string;
  customerSurname: string;
  customerAddress: string;
  customerAddInfo: string;
  orderState: string;
}


@Component({
  selector: 'lista-ordini',
  imports: [MatTableModule],
  templateUrl: './lista-ordini.html',
  styleUrl: './lista-ordini.scss',
})
export class ListaOrdini implements OnInit {
  displayedColumns: string[] = ['orderId', 'customerName', 'customerSurname', 'customerAddress', 'customerAddInfo', 'orderState'];
  dataSource: OrderData[] = [];

  private orderStates: OrderState[] = [];
  private orders: Order[] = [];

  constructor(private orderService: Orders, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ data }) => {
      console.log(data);
      this.orderStates = data.states.data;
      this.orders = data.orders.data;

      if(this.orders && this.orders.length > 0) {
        this.orders.forEach(((order, index) => {
          let orderState = this.orderStates.find(o => o.stateId == order.orderState)?.stateName;

          if(orderState == undefined) {
            orderState = 'STATO NON DISPONIBILE';
          }

          this.dataSource.push({
            orderId: order.orderId,
            orderNumber: index + 1,
            customerName: order.customerName,
            customerSurname: order.customerSurname,
            customerAddress: order.customerAddress + ', ' + order.customerStreetNumber,
            customerAddInfo: order.customerAddInfo,
            orderState: orderState
          })
        }))
      }

      console.log(this.dataSource);
    });
  }

  onClickRow(orderId: number) {
    console.log(orderId);
  }
}
