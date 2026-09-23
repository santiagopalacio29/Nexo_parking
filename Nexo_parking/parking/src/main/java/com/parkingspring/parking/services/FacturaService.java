package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> listarFactura() {
        return facturaRepository.listar();
    }

    public Factura obtenerFactura(Integer id) {
        return facturaRepository.obtenerPorId(id);
    }

    public Factura crearFactura(Factura factura) {
        return facturaRepository.insertar(factura);
    }

    public Factura actualizarFactura(Integer id, Factura factura) {
        factura.setIdFactura(id);
        return facturaRepository.actualizar(factura);
    }

    public boolean eliminarFactura(Integer id) {
        return facturaRepository.eliminar(id);
    }
}
