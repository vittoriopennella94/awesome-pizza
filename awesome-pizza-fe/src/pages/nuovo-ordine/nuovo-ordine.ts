import {ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import {MatDialog} from "@angular/material/dialog";
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {
    ProductSelectionDialogComponent
} from "../../components/product-selection-dialog/product-selection-dialog.component";
import {OrderService, Product} from "../../service/orders/order.service";

export interface OrderFormData {
    customerName: string;
    customerSurname: string;
    customerAddress: string;
    customerStreetNumber: string;
    customerAddInfo: string;
    customerPhoneNumber: string;
    products: OrderProductForm[];
}

export interface OrderProductForm {
    productId: number;
    productName: string;
    quantity: number;
    note: string;
}


@Component({
    selector: 'nuovo-ordine',
    imports: [MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, MatDividerModule, MatIconModule, MatCard, MatCardContent, ReactiveFormsModule, RouterLink],
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './nuovo-ordine.html',
    styleUrl: './nuovo-ordine.scss',
})
export class NuovoOrdine implements OnInit {
    readonly dialog = inject(MatDialog);

    orderForm!: FormGroup;

    productList: Product[] = [];

    constructor(private activatedRoute: ActivatedRoute,
                private fb: FormBuilder,
                private cdr: ChangeDetectorRef,
                private orderService: OrderService) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({data}) => {
            console.log(data);
            if(data && data.products && data.products.data && data.products.data.length > 0) {
                this.productList = data.products.data;
            }
        });

        this.initForm();
    }




    initForm(): void {
        this.orderForm = this.fb.group({
            customerName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
            customerSurname: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
            customerAddress: ['', [Validators.required, Validators.maxLength(150)]],
            customerStreetNumber: ['', [Validators.required,  Validators.maxLength(10)]],
            customerAddInfo: ['', [ Validators.maxLength(255)]],
            customerPhoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
            products: this.fb.array([])
        });
    }

    get products(): FormArray {
        return this.orderForm.get('products') as FormArray;
    }

    // Apri dialog per selezionare prodotti
    openProductDialog(): void {
        const dialogRef = this.dialog.open(ProductSelectionDialogComponent, {
            width: '600px',
            data: { selectedProducts: this.products.value , productList: this.productList}
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.addProduct(result);
            }
        });
    }

    // Aggiungi prodotto all'array
    addProduct(product: { productId: number; productName: string }): void {
        const productForm = this.fb.group({
            productId: [product.productId, Validators.required],
            productName: [product.productName, Validators.required],
            quantity: [1, [Validators.required, Validators.min(1)]],
            note: ['']
        });

        this.products.push(productForm);
        this.products.updateValueAndValidity();
        this.cdr.detectChanges();
    }

    // Rimuovi prodotto
    removeProduct(index: number): void {
        this.products.removeAt(index);
    }

    // Aumenta quantità
    increaseQuantity(index: number): void {
        const quantityControl = this.products.at(index).get('quantity');
        if (quantityControl) {
            quantityControl.setValue(quantityControl.value + 1);
        }
    }

    // Diminuisci quantità
    decreaseQuantity(index: number): void {
        const quantityControl = this.products.at(index).get('quantity');
        if (quantityControl && quantityControl.value > 1) {
            quantityControl.setValue(quantityControl.value - 1);
        }
    }

    // Submit del form
    onSubmit(): void {
        if (this.orderForm.valid) {
            console.log('Form valido:', this.orderForm.value);

            this.orderService.insertOrder(this.orderForm.value).subscribe({
                next: () => {
                    this.orderForm.reset();
                    this.products.clear();
                    alert('Ordine creato! Puoi crearne un altro.');
                }
            });

        } else {
            console.log('Form non valido');
            this.markFormGroupTouched(this.orderForm);
        }
    }

    // Segna tutti i campi come touched per mostrare gli errori
    private markFormGroupTouched(formGroup: FormGroup): void {
        Object.keys(formGroup.controls).forEach(key => {
            const control = formGroup.get(key);
            control?.markAsTouched();

            if (control instanceof FormGroup) {
                this.markFormGroupTouched(control);
            }
        });
    }

    // Reset del form
    onReset(): void {
        this.orderForm.reset();
        this.products.clear();
    }

    // Getter per errori
    getErrorMessage(fieldName: string): string {
        const field = this.orderForm.get(fieldName);

        if (field?.hasError('required')) {
            return 'Campo obbligatorio';
        }
        if (field?.hasError('minlength')) {
            return `Minimo ${field.errors?.['minlength'].requiredLength} caratteri`;
        }
        if (field?.hasError('min')) {
            return 'Il valore deve essere maggiore di 0';
        }

        return '';
    }
}