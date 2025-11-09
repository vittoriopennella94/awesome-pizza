import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../service/products/product.service';
import {Product} from "../../service/orders/order.service";

export interface ProductSelectionData {
    selectedProducts: any[];
    productList: Product[];
}

@Component({
    selector: 'app-product-selection-dialog',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        MatDialogModule,
        MatButtonModule,
        MatListModule,
        MatIconModule,
        MatProgressSpinnerModule,
        MatFormFieldModule,
        MatInputModule
    ],
    templateUrl: './product-selection-dialog.component.html',
    styleUrls: ['./product-selection-dialog.component.scss']
})
export class ProductSelectionDialogComponent implements OnInit {
    availableProducts: Product[] = [];
    filteredProducts: Product[] = [];
    selectedProduct: Product | null = null;
    loading: boolean = true;
    searchTerm: string = '';

    constructor(
        public dialogRef: MatDialogRef<ProductSelectionDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: ProductSelectionData,
        private productService: ProductService
    ) {}

    ngOnInit(): void {
        this.loadProducts();
    }

    loadProducts(): void {
        this.loading = true;

        this.availableProducts = this.data.productList;
        this.filteredProducts = [...this.availableProducts];

        this.loading = false;
        /*this.productService.searchAllProducts().subscribe({
            next: (res) => {
                if (res && res.data) {
                    this.availableProducts = res.data;
                    this.filteredProducts = [...this.availableProducts];

                    // Rimuovi prodotti già selezionati
                    if (this.data.selectedProducts && this.data.selectedProducts.length > 0) {
                        const selectedIds = this.data.selectedProducts.map(p => p.productId);
                        this.filteredProducts = this.availableProducts.filter(
                            p => !selectedIds.includes(p.productId)
                        );
                    }
                }
                this.loading = false;
            },
            error: (error) => {
                console.error('Errore caricamento prodotti:', error);
                this.loading = false;
            }
        });*/
    }

    onSearch(): void {
        const term = this.searchTerm.toLowerCase().trim();

        if (!term) {
            this.filteredProducts = [...this.availableProducts];
            return;
        }

        this.filteredProducts = this.availableProducts.filter(product =>
            product.productName.toLowerCase().includes(term) ||
            (product.productDescription && product.productDescription.toLowerCase().includes(term))
        );
    }

    onSelectProduct(product: Product): void {
        this.selectedProduct = product;
    }

    onConfirm(): void {
        if (this.selectedProduct) {
            this.dialogRef.close({
                productId: this.selectedProduct.productId,
                productName: this.selectedProduct.productName
            });
        }
    }

    onCancel(): void {
        this.dialogRef.close();
    }

    isSelected(product: Product): boolean {
        return this.selectedProduct?.productId === product.productId;
    }
}