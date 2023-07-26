package com.inventarioKonex.InventarioKonex.service;

import com.inventarioKonex.InventarioKonex.model.Dto.VentaDto;
import com.inventarioKonex.InventarioKonex.model.entity.Medicamentos;
import com.inventarioKonex.InventarioKonex.model.entity.Venta;
import com.inventarioKonex.InventarioKonex.repository.IMedicamentosRepository;
import com.inventarioKonex.InventarioKonex.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final IVentaRepository ventaRepository;

    private final IMedicamentosRepository medicamentosRepository;

    @Autowired
    public VentaService(IVentaRepository ventaRepository, IMedicamentosRepository medicamentosRepository) {
        this.ventaRepository = ventaRepository;
        this.medicamentosRepository = medicamentosRepository;
    }

    public List<Venta> makeSale(List<Long> medicamentosId, List<Integer> cantidades) throws Exception{
        if (medicamentosId.size() !=cantidades.size()){
            throw new IllegalAccessException("Seleccione la misma cantidad de elementos");
        }
        List<Venta> ventas  = new ArrayList<>();

        for (int  i = 0; i<medicamentosId.size(); i++){
            Long idMedicamento = medicamentosId.get(i);
            int cantidad = cantidades.get(i);

            Medicamentos medicamento = medicamentosRepository.findById(idMedicamento).orElse(null);

            if(medicamento == null){
                throw new Exception("No se encontro el Medicamento: " + idMedicamento);
            }
            double valorTotal = cantidad* medicamento.getValorUnitario();
            medicamento.setCantidadStock(medicamento.getCantidadStock() - cantidad);

            Venta venta = new Venta();
            venta.setMedicamentos(medicamento);
            venta.setCantidad(cantidad);
            venta.setValorUnitario(medicamento.getValorUnitario());
            venta.setValorTotal((float) valorTotal);
            venta.setFechaHora(LocalDateTime.now());

            ventaRepository.save(venta);
            ventas.add(venta);

            medicamentosRepository.save(medicamento);

        }
        return ventas;

    }

    public List<Venta> updateSale(Long ventaId, List<VentaDto> ventaRequestList) {
        List<Venta> updatedVentas = new ArrayList<>();

        for (VentaDto saleRequest : ventaRequestList) {
            Optional<Venta> optionalVenta = ventaRepository.findById(ventaId);
            if (optionalVenta.isPresent()) {
                Venta venta = optionalVenta.get();

                venta.setCantidad(saleRequest.getCantidad());
                venta.setValorUnitario((float) saleRequest.getValorUnitario());
                venta.setValorTotal((float) (venta.getCantidad() * saleRequest.getValorUnitario()));
                venta.setFechaHora(LocalDateTime.now());

                Venta updatedVenta = ventaRepository.save(venta);
                updatedVentas.add(updatedVenta);
            }
        }

        return updatedVentas;
    }


    public void DeleteSale(Long ventaId) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElse(null);
        if (venta == null) {
            throw new Exception("Venta con ID " + ventaId + " no encontrada.");
        }
        Medicamentos medicamento = venta.getMedicamentos();
        medicamento.setCantidadStock(medicamento.getCantidadStock() + venta.getCantidad());
        ventaRepository.deleteById(ventaId);
    }

    public List<Venta> salesByDate(LocalDateTime fechaI, LocalDateTime fechaF) {
        return ventaRepository.findByFechaHoraBetween(fechaI, fechaF);
    }

    public Venta findSaleById(Long id){
        Optional<Venta> ventaOpcional= ventaRepository.findById(id);
        if(ventaOpcional.isPresent()){
            return ventaOpcional.get();
        }else{
            return null;
        }
    }

    public List<Venta> getAllSales(){
        return ventaRepository.findAll();
    }

}
