package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Cliente;

import java.util.List;

public interface ClienteServiceInterface {
    List<Cliente> listarCliente();
    Cliente obtenerPorId(Integer id);
    Cliente crearCliente(Cliente cliente);
    Cliente actualizarCliente(Integer id, Cliente cliente);
    boolean eliminarCliente(Integer id);
}