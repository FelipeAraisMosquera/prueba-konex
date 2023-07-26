package com.inventarioKonex.InventarioKonex.service;

import com.inventarioKonex.InventarioKonex.model.entity.Medicamentos;
import com.inventarioKonex.InventarioKonex.repository.IMedicamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicamentoService {
    private final IMedicamentosRepository medicamentosRepository;

    @Autowired
    public MedicamentoService(IMedicamentosRepository medicamentosRepository) {
        this.medicamentosRepository = medicamentosRepository;
    }

    public void saveMedicamentos(Medicamentos medicamentos){
        this.medicamentosRepository.save(medicamentos);
    }

    public Medicamentos findMedicamentosById(Long id){
        Optional<Medicamentos> medicamentoOpcional= medicamentosRepository.findById(id);
        if(medicamentoOpcional.isPresent()){
            return medicamentoOpcional.get();
        }else{
            return null;
        }
    }

    public void deleteMedicamentosById(Long id) {
        medicamentosRepository.deleteById(id);
    }

    public void updateMedicamentos(Medicamentos medicamento) {
        medicamentosRepository.save(medicamento);
    }

    public Page<Medicamentos> getMedicamentos(String nombre, String laboratorio, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return medicamentosRepository.findByNombreContainingIgnoreCaseOrLaboratorioContainingIgnoreCase(nombre, laboratorio, pageable);
    }

}
