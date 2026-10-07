package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Factura;

import java.util.List;

public interface FacturaRepositoryInterface {
    List<Factura> listarFactura();
    Factura obtenerFactura(Integer id);
    Factura insertarFactura(Factura factura);
    Factura actualizarFactura(Factura factura);
    boolean eliminarFactura(Integer id);
}
