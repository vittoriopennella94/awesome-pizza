import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovoOrdine } from './nuovo-ordine';

describe('NuovoOrdine', () => {
  let component: NuovoOrdine;
  let fixture: ComponentFixture<NuovoOrdine>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NuovoOrdine]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NuovoOrdine);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
