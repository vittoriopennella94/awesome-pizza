import {Component, Inject} from '@angular/core';
import {ChangeDetectionStrategy} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {
    MAT_DIALOG_DATA,
    MatDialog,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent, MatDialogRef,
    MatDialogTitle,
} from '@angular/material/dialog';


export interface ProductDetailsDialogData {
    title: string;
    description: string;
}

@Component({
    selector: 'product-details-dialog',
    imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule],
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './product-details-dialog.component.html',
    styleUrl: './product-details-dialog.component.scss',
})
export class ProductDetailsDialogComponent {
    constructor(public dialogRef: MatDialogRef<ProductDetailsDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: ProductDetailsDialogData) {
    }
}
