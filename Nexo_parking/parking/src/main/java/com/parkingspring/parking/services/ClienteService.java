package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Cliente;
import com.parkingspring.parking.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.listar();
    }

    public Cliente obtenerCliente(Integer id) {
        return clienteRepository.obtenerPorId(id);
    }

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.insertar(cliente);
    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) {
        cliente.setIdCliente(id);
        return clienteRepository.actualizar(cliente);
    }

    public boolean eliminarCliente(Integer id) {
        return clienteRepository.eliminar(id);
    }
}
