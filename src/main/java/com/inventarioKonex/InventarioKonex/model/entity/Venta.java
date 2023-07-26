package com.inventarioKonex.InventarioKonex.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamentos medicamentos;

    private int cantidad;
    private float  valorUnitario;
    private float valorTotal;
    private LocalDateTime fechaHora;


}
