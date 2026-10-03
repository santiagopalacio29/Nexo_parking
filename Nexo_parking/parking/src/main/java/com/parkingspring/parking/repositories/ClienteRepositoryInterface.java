package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Cliente;

import java.util.List;

public interface ClienteRepositoryInterface {
    List<Cliente> listarCliente();
    Cliente obtenerPorId(Integer id);
    Cliente crearCliente(Cliente cliente);
    Cliente actualizarCliente(Cliente cliente);
    boolean eliminarCliente(Integer id);
}