// error-page.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {MatIconModule} from "@angular/material/icon";
import {MatCard} from "@angular/material/card";
import {MatButton} from "@angular/material/button";

@Component({
    selector: 'error-page',
    imports: [MatIconModule, MatCard, MatButton],
    templateUrl: './errore.component.html',
    styleUrls: ['./errore.component.scss']
})
export class ErroreComponent {

    constructor(private router: Router) {}

    goToHome(): void {
        this.router.navigate(['/']);
    }
}