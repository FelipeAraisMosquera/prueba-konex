package com.inventarioKonex.InventarioKonex.controller;

import com.inventarioKonex.InventarioKonex.model.Dto.VentaDto;
import com.inventarioKonex.InventarioKonex.model.entity.Venta;
import com.inventarioKonex.InventarioKonex.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/venta")
public class VentaController {

    private final VentaService ventaService;

    @Autowired
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<List<Venta>> makeSale(@RequestBody List<VentaDto> requestSale){
      try{
          List<Long> medicamentosId = new ArrayList<>();
          List<Integer> cantidad = new ArrayList<>();

          for (VentaDto requetSale : requestSale){
              medicamentosId.add(requetSale.getMedicamentoId());
              cantidad.add(requetSale.getCantidad());
          }

          List<Venta> salesMade = ventaService.makeSale(medicamentosId, cantidad);

          return new ResponseEntity<>(salesMade, HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/{ventaId}")
    public ResponseEntity<Venta> UpdateSale(
            @PathVariable Long ventaId,
            @RequestBody List<VentaDto> saleRequestList
    ) {
        List<Venta> sales = ventaService.updateSale(ventaId, saleRequestList);
        if(!sales.isEmpty()){
            return ResponseEntity.ok(sales.get(0));
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(value="/filtrar")
    public List<Venta> salesByDate (
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime fechaI,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaF){
        if(fechaI != null && fechaF != null){
            return ventaService.salesByDate(fechaI,fechaF);
        }else{
            return ventaService.getAllSales();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteSale(@PathVariable Long id) throws Exception {
        Venta venta = ventaService.findSaleById(id);
        if(venta != null) {
            ventaService.DeleteSale(id);
            return ResponseEntity.ok("La venta ha sido eliminado correctamente.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
