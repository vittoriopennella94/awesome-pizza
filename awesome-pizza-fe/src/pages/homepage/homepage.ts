import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatIconModule} from "@angular/material/icon";
import {MatCard, MatCardContent} from "@angular/material/card";

@Component({
  selector: 'homepage',
  imports: [CommonModule, MatIconModule, MatCard, MatCardContent],
  templateUrl: './homepage.html',
  styleUrl: './homepage.scss',
})
export class Homepage {
  constructor(private router: Router) {}

    navigateToNewOrder(): void {
        // Naviga alla pagina di creazione nuovo ordine
        this.router.navigate(['nuovo-ordine']);
    }

    navigateToOrderList(): void {
        // Naviga alla lista degli ordini
        this.router.navigate(['lista-ordini']);
    }
}
