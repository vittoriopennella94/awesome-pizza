import {Component, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {Order} from "../../service/orders/order.service";

@Component({
  selector: 'dettaglio-ordine',
  imports: [CommonModule],
  templateUrl: './dettaglio-ordine.component.html',
  styleUrl: './dettaglio-ordine.component.scss',
})
export class DettaglioOrdineComponent implements OnInit {
    private orderDetails: Order | undefined;

    constructor(private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ data }) => {
            console.log(data);
            if(data && data?.data){
                this.orderDetails = data.data;
            }
            console.log(this.orderDetails);
        });
    }
}
