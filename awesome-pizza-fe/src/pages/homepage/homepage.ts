import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'homepage',
  imports: [CommonModule],
  templateUrl: './homepage.html',
  styleUrl: './homepage.scss',
})
export class Homepage {
  constructor(private router: Router) {}


  onClickOrdina() {
    this.router.navigateByUrl("nuovo-ordine").then(r => {});
  }

  onClickListaOrdini() {
    this.router.navigateByUrl("lista-ordini").then(r => {});
  }
}
