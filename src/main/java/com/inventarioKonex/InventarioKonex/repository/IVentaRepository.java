package com.inventarioKonex.InventarioKonex.repository;

import com.inventarioKonex.InventarioKonex.model.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaHoraBetween(LocalDateTime fechaI, LocalDateTime fechaF);
}
