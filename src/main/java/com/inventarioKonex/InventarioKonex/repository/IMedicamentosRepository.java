package com.inventarioKonex.InventarioKonex.repository;

import com.inventarioKonex.InventarioKonex.model.entity.Medicamentos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMedicamentosRepository extends JpaRepository<Medicamentos, Long> {

    Medicamentos save(Medicamentos medicamentos);

    Optional<Medicamentos> findMedicamentosById(Long id);

    void deleteById(Long id);

    Page<Medicamentos> findByNombreContainingIgnoreCaseOrLaboratorioContainingIgnoreCase(String Nombre, String Laboratorio, Pageable pageable);
}

