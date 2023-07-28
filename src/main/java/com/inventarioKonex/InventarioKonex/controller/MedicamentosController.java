package com.inventarioKonex.InventarioKonex.controller;

import com.inventarioKonex.InventarioKonex.model.Dto.MedicamentosDto;
import com.inventarioKonex.InventarioKonex.model.entity.Medicamentos;
import com.inventarioKonex.InventarioKonex.repository.IMedicamentosRepository;
import com.inventarioKonex.InventarioKonex.service.MedicamentoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/medicamentos")
public class MedicamentosController {

    private MedicamentoService medicamentoService;
    private IMedicamentosRepository medicamentosRepository;

    public MedicamentosController(MedicamentoService medicamentoService, IMedicamentosRepository medicamentosRepository) {
        this.medicamentoService = medicamentoService;
        this.medicamentosRepository = medicamentosRepository;
    }


    @PostMapping()//Crear
        public ResponseEntity<String> saveMedicamentos(@RequestBody MedicamentosDto medicamentosDTO){
            if(medicamentosDTO != null){
                Medicamentos medicamento = new Medicamentos();
                medicamento.setNombre(medicamentosDTO.getNombre());
                medicamento.setLaboratorio(medicamentosDTO.getLaboratorio());
                medicamento.setFechaFabricacion(medicamentosDTO.getFechaFabricacion());
                medicamento.setFechaVencimiento(medicamentosDTO.getFechaVencimiento());
                medicamento.setCantidadStock(medicamentosDTO.getCantidadStock());
                medicamento.setValorUnitario(medicamentosDTO.getValorUnitario());

                medicamentoService.saveMedicamentos(medicamento);
                return ResponseEntity.ok("El objeto materiales se ha guardado correctamente.");
            } else {
                return ResponseEntity.badRequest().body("Los datos del objeto Materiales son inválidos.");
            }


    }

    @GetMapping//Listar
    public List<Medicamentos> getAllMedicaments(){
        return medicamentoService.getAllDrugs();
    }

    @GetMapping("/{id}")//Consultar
    public Medicamentos findMedicamento(@PathVariable Long id){
        return medicamentoService.findMedicamentosById(id);
    }

    @DeleteMapping("/{id}") // Eliminar
    public ResponseEntity<String> borrarMedicamentos(@PathVariable Long id) {
        Medicamentos medicamento = medicamentoService.findMedicamentosById(id);
        if (medicamento != null) {
            medicamentoService.deleteMedicamentosById(id);
            return ResponseEntity.ok("El medicamento ha sido eliminado correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}") // Actualizar
    public ResponseEntity<String> actualizarMedicamentos(@PathVariable Long id, @RequestBody MedicamentosDto medicamentosDTO) {
        Medicamentos medicamentoExistente = medicamentoService.findMedicamentosById(id);

        if (medicamentoExistente != null) {
            // Actualizar los campos del medicamento existente con los datos del DTO
            medicamentoExistente.setNombre(medicamentosDTO.getNombre());
            medicamentoExistente.setLaboratorio(medicamentosDTO.getLaboratorio());
            medicamentoExistente.setFechaFabricacion(medicamentosDTO.getFechaFabricacion());
            medicamentoExistente.setFechaVencimiento(medicamentosDTO.getFechaVencimiento());
            medicamentoExistente.setCantidadStock(medicamentosDTO.getCantidadStock());
            medicamentoExistente.setValorUnitario(medicamentosDTO.getValorUnitario());

            medicamentoService.updateMedicamentos(medicamentoExistente);
            return ResponseEntity.ok("El medicamento ha sido actualizado correctamente.");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   @GetMapping("/page")
    public ResponseEntity<Page<Medicamentos>> getMedicaments(
            @RequestParam(required = false) String Nombre,
            @RequestParam(required = false) String Laboratorio,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Medicamentos> medicamentPage = medicamentoService.getMedicamentos(Nombre, Laboratorio, pageNumber, pageSize);
        return new ResponseEntity<>(medicamentPage, HttpStatus.OK);
    }
}





