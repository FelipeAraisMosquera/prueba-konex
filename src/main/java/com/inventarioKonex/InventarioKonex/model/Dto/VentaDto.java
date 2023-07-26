package com.inventarioKonex.InventarioKonex.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VentaDto {
    private Long id;
    private Long medicamentoId;
    private int cantidad;
    private double valorUnitario;
    private double valorTotal;
}
