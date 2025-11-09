import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Order, OrderProduct, OrderService, OrderState, Product} from "../../service/orders/order.service";
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatDialog} from "@angular/material/dialog";
import {ProductDetailsDialogComponent} from "../../components/product-details-dialog/product-details-dialog.component";
import {ProductService} from "../../service/products/product.service";
import {MatMenuModule} from "@angular/material/menu";
import {MatDividerModule} from "@angular/material/divider";


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
    imports: [MatTableModule, MatButtonModule, MatIconModule, MatMenuModule, MatDividerModule],
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

    constructor(private activatedRoute: ActivatedRoute,
                private router: Router,
                private orderService: OrderService,
                private productService: ProductService) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({data}) => {
            console.log(data);
            this.orderStates = data.states.data;
            this.orders = data.orders.data;

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
                    })
                }))
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


    // Azioni del menu
    onView(element: Order): void {
        console.log('Visualizza:', element);
        // Naviga alla pagina dettaglio o apri dialog
    }

    onEdit(element: Order): void {
        console.log('Modifica:', element);
        // Apri dialog di modifica
    }

    onDelete(element: Order): void {
        console.log('Elimina:', element);
    }

    onDuplicate(element: Order): void {
        console.log('Duplica:', element);
        // Logica per duplicare
    }
}