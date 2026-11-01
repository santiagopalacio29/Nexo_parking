package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Cliente;
import com.parkingspring.parking.repositories.ClienteJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClienteJpaIntegrationTest {

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @Test
    @Transactional 
    void probarConsultaYRelacionJpa() {
        List<Cliente> clientes = clienteJpaRepository.findAll();
        
        assertNotNull(clientes, "La lista de clientes no debería ser nula");

        for (Cliente cliente : clientes) {
            System.out.println("--- Cliente encontrado por JPA: " + cliente.getNombre() + " ---");
            if (cliente.getVehiculos() != null) {
                // Ahora sí podrá consultar los vehículos porque la transacción está activa
                System.out.println("Cantidad de vehículos: " + cliente.getVehiculos().size());
            }
        }
    }
}