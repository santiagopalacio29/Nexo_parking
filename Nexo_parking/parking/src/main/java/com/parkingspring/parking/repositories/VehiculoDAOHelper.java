package com.parkingspring.parking.repositories;

import org.springframework.stereotype.Component;

@Component
public class VehiculoDAOHelper {

    public String listarVehiculo() {
        return "SELECT placa, marca, color, tipo_vehiculo, documento_cliente FROM vehiculo";
    }

    public String obtenerVehiculo() {
        return "SELECT placa, marca, color, tipo_vehiculo, documento_cliente FROM vehiculo WHERE placa = ?";
    }

    public String insertarVehiculo() {
        return "INSERT INTO vehiculo (placa, marca, color, tipo_vehiculo, documento_cliente) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarVehiculo() {
        return "UPDATE vehiculo SET placa = ?, marca = ?, color = ?, tipo_vehiculo = ?, documento_cliente = ? WHERE placa = ?";
    }

    public String eliminarVehiculo() {
        return "DELETE FROM vehiculo WHERE placa = ?";
    }
}
