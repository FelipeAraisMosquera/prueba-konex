package com.inventarioKonex.InventarioKonex.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "medicamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Medicamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nombre;
    private String laboratorio;
    private Date fechaFabricación;
    private Date fechaVencimiento;
    private int cantidadStock;
    private int valorUnitario;



}
