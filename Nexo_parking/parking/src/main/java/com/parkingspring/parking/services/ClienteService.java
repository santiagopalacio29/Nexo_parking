package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Cliente;
import com.parkingspring.parking.repositories.ClienteRepository;
import com.parkingspring.parking.repositories.ClienteRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements ClienteServiceInterface{

    private final ClienteRepositoryInterface clienteRepository;

    public ClienteService(ClienteRepositoryInterface clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarCliente() {
        return clienteRepository.listarCliente();
    }

    public Cliente obtenerPorId(Integer id) {
        return clienteRepository.obtenerPorId(id);
    }

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.crearCliente(cliente);
    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) {
        cliente.setIdCliente(id);
        return clienteRepository.actualizarCliente(cliente);
    }

    public boolean eliminarCliente(Integer id) {
        return clienteRepository.eliminarCliente(id);
    }
}
