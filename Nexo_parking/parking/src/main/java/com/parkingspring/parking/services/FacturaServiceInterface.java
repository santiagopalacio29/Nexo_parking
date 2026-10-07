package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Factura;
import java.util.List;

public interface FacturaServiceInterface {
    List<Factura> listarFactura();
    Factura obtenerFactura(Integer id);
    Factura insertarFactura(Factura factura);
    Factura actualizarFactura(Integer id, Factura factura);
    boolean eliminarFactura(Integer id);
}
