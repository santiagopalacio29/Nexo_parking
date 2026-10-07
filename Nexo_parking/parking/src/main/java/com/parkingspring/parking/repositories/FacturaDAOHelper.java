package com.parkingspring.parking.repositories;

import org.springframework.stereotype.Component;

@Component
public class FacturaDAOHelper {

    public String listarFacturas() {
        return "SELECT id_factura, id_registro, fecha_pago, valor_total, metodo_pago FROM factura";
    }

    public String obtenerFactura() {
        return "SELECT id_factura, id_registro, fecha_pago, valor_total, metodo_pago FROM factura WHERE id_factura = ?";
    }

    public String insertarFactura() {
        return "INSERT INTO factura (id_factura, id_registro, fecha_pago, valor_total, metodo_pago) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarFactura() {
        return "UPDATE factura SET id_factura = ?, id_registro = ?, fecha_pago = ?, valor_total = ?, metodo_pago = ? WHERE id_factura = ?";
    }

    public String eliminarFactura() {
        return "DELETE FROM factura WHERE id_factura = ?";
    }
}
