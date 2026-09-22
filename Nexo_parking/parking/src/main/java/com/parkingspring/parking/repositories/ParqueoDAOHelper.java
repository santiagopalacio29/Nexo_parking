package com.parkingspring.parking.repositories;

import org.springframework.stereotype.Component;

@Component
public class ParqueoDAOHelper {

    public String listarRegistros() {
        return "SELECT id_registro, placa, id_espacio, fecha_hora_entrada, fecha_hora_salida, tiempo_total " +
                "FROM registro_parqueo_horas";
    }

    public String obtenerRegistro() {
        return "SELECT id_registro, placa, id_espacio, fecha_hora_entrada, fecha_hora_salida, tiempo_total " +
                "FROM registro_parqueo_horas WHERE id_registro = ?";
    }

    public String registrarEntrada() {
        return "INSERT INTO registro_parqueo_horas (placa, id_espacio, fecha_hora_entrada) VALUES (?, ?, ?)";
    }

    public String registrarSalida() {
        return "UPDATE registro_parqueo_horas SET fecha_hora_salida = ?, tiempo_total = ? WHERE id_registro = ?";
    }

    public String ocuparEspacio() {
        return "UPDATE espacio SET estado = FALSE WHERE id_espacio = ?";
    }

    public String liberarEspacio() {
        return "UPDATE espacio SET estado = TRUE WHERE id_espacio = ?";
    }

    public String obtenerTarifaPorTipoVehiculo() {
        return "SELECT t.valor_hora FROM tarifa t " +
                "INNER JOIN vehiculo v ON v.tipo_vehiculo = t.tipo_vehiculo " +
                "WHERE v.placa = (SELECT placa FROM registro_parqueo_horas WHERE id_registro = ?)";
    }

    public String insertarFactura() {
        return "INSERT INTO factura (id_registro, fecha_pago, valor_total, metodo_pago) VALUES (?, ?, ?, ?)";
    }
}
