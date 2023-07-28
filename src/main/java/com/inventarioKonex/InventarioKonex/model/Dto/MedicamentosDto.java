package com.inventarioKonex.InventarioKonex.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
public class MedicamentosDto {
   private Long id;
   private String nombre;
   private String laboratorio;
   private Date fechaFabricacion;
   private Date fechaVencimiento;
   private int cantidadStock;
   private int valorUnitario;

}
