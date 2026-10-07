package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.repositories.FacturaRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService implements FacturaServiceInterface{

    private final FacturaRepositoryInterface facturaRepository;

    public FacturaService(FacturaRepositoryInterface facturaRepository){
        this.facturaRepository = facturaRepository;
    }

    public List<Factura> listarFactura() {
        return facturaRepository.listarFactura();
    }

    public Factura obtenerFactura(Integer id) {
        return facturaRepository.obtenerFactura(id);
    }

    public Factura insertarFactura(Factura factura) {
        return facturaRepository.insertarFactura(factura);
    }

    public Factura actualizarFactura(Integer id, Factura factura) {
        factura.setIdFactura(id);
        return facturaRepository.actualizarFactura(factura);
    }

    public boolean eliminarFactura(Integer id) {
        return facturaRepository.eliminarFactura(id);
    }
}
