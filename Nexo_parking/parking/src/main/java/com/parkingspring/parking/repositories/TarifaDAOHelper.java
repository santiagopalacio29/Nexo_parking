package com.parkingspring.parking.repositories;

import org.springframework.stereotype.Component;

@Component
public class TarifaDAOHelper {

    public String listarTarifas() {
        return "SELECT id_tarifa, tipo_vehiculo, valor_hora, valor_dia, valor_mensualidad FROM tarifa";
    }

    public String obtenerTarifas() {
        return "SELECT id_tarifa, tipo_vehiculo, valor_hora, valor_dia, valor_mensualidad FROM tarifa WHERE id_tarifa = ?";
    }

    public String insertarTarifa() {
        return "INSERT INTO tarifa (id_tarifa, tipo_vehiculo, valor_hora, valor_dia, valor_mensualidad) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarTarifa() {
        return "UPDATE tarifa SET id_tarifa = ?, tipo_vehiculo = ?, valor_hora = ?, valor_dia = ?, valor_mensualidad = ? WHERE id_tarifa = ?";
    }

    public String eliminarTarifa() {
        return "DELETE FROM tarifa WHERE id_tarifa = ?";
    }
}
