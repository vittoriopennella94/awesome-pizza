import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {
    Order,
    OrderProduct,
    OrderService,
    OrderState,
    Product,
    UpdateOrderData
} from "../../service/orders/order.service";
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import {ProductDetailsDialogComponent} from "../../components/product-details-dialog/product-details-dialog.component";
import {ProductService} from "../../service/products/product.service";
import {MatMenuModule} from "@angular/material/menu";
import {MatDividerModule} from "@angular/material/divider";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatCardModule} from "@angular/material/card";


export interface OrderData {
    orderId: number;
    orderNumber: number;
    customerName: string;
    customerSurname: string;
    customerAddress: string;
    customerAddInfo: string;
    orderState: string;
}

export interface HeaderTableData {
    id: string;
    name: string;
}

@Component({
    selector: 'lista-ordini',
    imports: [MatTableModule, MatButtonModule, MatIconModule, MatMenuModule, MatDividerModule, MatProgressSpinnerModule,
        MatCardModule, RouterLink],
    templateUrl: './lista-ordini.html',
    styleUrl: './lista-ordini.scss',
})
export class ListaOrdini implements OnInit {
    readonly dialog = inject(MatDialog);

    header: HeaderTableData[] = [
        {
            id: 'orderNumber',
            name: 'No.',
        },
        {
            id: 'customerName',
            name: 'Name',
        },
        {
            id: 'customerSurname',
            name: 'Surname',
        },
        {
            id: 'customerAddress',
            name: 'Address',
        },
        {
            id: 'customerAddInfo',
            name: 'Add. Info',
        },
        {
            id: 'orderState',
            name: 'State',
        }
    ]
    columnsToDisplayWithExpand = [...this.header.map(h => h.id), 'actions', 'expand'];
    expandedElement: OrderData | null | undefined;
    dataSource: OrderData[] = [];
    orderDetails: OrderProduct[] | undefined | null = null;

    private orderStates: OrderState[] = [];
    private orders: Order[] = [];
    private ordersByState: Order[] = [];
    private selectedState = 'ALL';

    constructor(private activatedRoute: ActivatedRoute,
                private router: Router,
                private orderService: OrderService,
                private productService: ProductService) {
    }

    ngOnInit() {
        this.selectedState = 'ALL';

        this.activatedRoute.data.subscribe(({data}) => {
            console.log(data);
            this.orderStates = data.states.data;
            this.orders = data.orders.data;
            this.ordersByState = this.orders;

            if (this.orders && this.orders.length > 0) {
                this.orders.forEach(((order, index) => {
                    this.dataSource.push({
                        orderId: order.orderId,
                        orderNumber: index + 1,
                        customerName: order.customerName,
                        customerSurname: order.customerSurname,
                        customerAddress: order.customerAddress + ', ' + order.customerStreetNumber,
                        customerAddInfo: order.customerAddInfo,
                        orderState: order.orderState
                    });
                }));

                this.onChangeFilterState(this.selectedState);
            }

            console.log(this.dataSource);
        });
    }

    onClickProductDetails(productId: number) {
        console.log(productId);

        this.productService.getProductById(productId).subscribe(res => {
            if (res && res.data) {
                this.dialog.open(ProductDetailsDialogComponent, {
                    data: {
                        title: res.data.productName,
                        description: res.data.productDescription,
                    }
                });
            }
        });
    }

    isExpanded(element: OrderData) {
        return this.expandedElement === element;
    }

    toggle(element: OrderData) {
        this.orderDetails = null;
        this.expandedElement = this.isExpanded(element) ? null : element;

        if(this.expandedElement) {
            this.orderService.getOrderDetails(element.orderId).subscribe(res => {
                console.log(res);
                if(res && res.success && res.data){
                    this.orderDetails = res.data;
                }
            })
        }
    }


// Helper per le classi CSS degli stati
    getStatusClass(status: string): string {
        return status.toLowerCase().replace(/\s+/g, '-');
    }

// Azioni del menu
    onTakeCharge(element: OrderData): void {
        console.log('Prendi in carico:', element);
        // Aggiorna stato a "IN PREPARAZIONE"
        // this.orderService.updateOrderState(element.orderId, 'IN PREPARAZIONE')

        this.updateOrderState(element.orderId, "IN PREPARAZIONE");
    }

    onStartDelivery(element: OrderData): void {
        console.log('Avvia consegna:', element);
        // Aggiorna stato a "IN CONSEGNA"
        // this.orderService.updateOrderState(element.orderId, 'IN CONSEGNA')

        this.updateOrderState(element.orderId, "IN CONSEGNA");
    }

    onComplete(element: OrderData): void {
        console.log('Completa ordine:', element);
        // Aggiorna stato a "CONSEGNATO"
        // this.orderService.updateOrderState(element.orderId, 'CONSEGNATO')

        this.updateOrderState(element.orderId, "CONSEGNATO");
    }

    onCancel(element: OrderData): void {
        if (confirm(`Vuoi annullare l'ordine #${element.orderNumber}?`)) {
            console.log('Annulla ordine:', element);
            // Aggiorna stato a "ANNULLATO"
            // this.orderService.updateOrderState(element.orderId, 'ANNULLATO')
            this.updateOrderState(element.orderId, "ANNULLATO");
        }
    }

    onChangeFilterState(state: string){
        console.log("STATO SELEZIONATO: " + state);
        if(state){
            this.selectedState = state;
            if(state === 'ALL'){
                this.ordersByState = this.orders;
            }else {
                this.ordersByState = this.orders.filter(order => order.orderState === state);
            }

            this.dataSource = [];
            this.ordersByState.forEach(((order, index) => {
                this.dataSource.push({
                    orderId: order.orderId,
                    orderNumber: index + 1,
                    customerName: order.customerName,
                    customerSurname: order.customerSurname,
                    customerAddress: order.customerAddress + ', ' + order.customerStreetNumber,
                    customerAddInfo: order.customerAddInfo,
                    orderState: order.orderState
                })
            }))
        }
    }

    private updateOrderState(orderId: number, stateName: string) {
        let nextState = this.orderStates.find(o => o.stateName === stateName)?.stateId ?? 0;

        const body: UpdateOrderData = {
            orderId: orderId,
            stateId: nextState,
        }
        this.orderService.updateOrder(body).subscribe(res => {
            console.log(res);
            if(res && res.data) {
                this.updateDataSource();
            }
        })
    }

    private updateDataSource(){
        this.orderService.getAllOrders().subscribe(res => {
            console.log(res);
            if(res && res.data && res.data.length > 0) {
                this.orders = res.data;
                this.ordersByState = this.orders;
                this.dataSource = [];
                this.orders.forEach(((order, index) => {
                    this.dataSource.push({
                        orderId: order.orderId,
                        orderNumber: index + 1,
                        customerName: order.customerName,
                        customerSurname: order.customerSurname,
                        customerAddress: order.customerAddress + ', ' + order.customerStreetNumber,
                        customerAddInfo: order.customerAddInfo,
                        orderState: order.orderState
                    });
                }));

                this.onChangeFilterState(this.selectedState);
            }
        })
    }
}