import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaOrdini } from './lista-ordini';

describe('ListaOrdini', () => {
  let component: ListaOrdini;
  let fixture: ComponentFixture<ListaOrdini>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaOrdini]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaOrdini);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
