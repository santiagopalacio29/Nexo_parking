package com.parkingspring.parking.repositories;

import org.springframework.stereotype.Component;

@Component
public class ClienteDAOHelper {

    public String listarClientes() {
        return "SELECT id_cliente, documento, nombre, apellido, telefono, email FROM cliente";
    }

    public String obtenerCliente() {
        return "SELECT id_cliente, documento, nombre, apellido, telefono, email FROM cliente WHERE id_cliente = ?";
    }

    public String insertarCliente() {
        return "INSERT INTO cliente (documento, nombre, apellido, telefono, email) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarCliente() {
        return "UPDATE cliente SET documento = ?, nombre = ?, apellido = ?, telefono = ?, email = ? WHERE id_cliente = ?";
    }

    public String eliminarCliente() {
        return "DELETE FROM cliente WHERE id_cliente = ?";
    }
}
