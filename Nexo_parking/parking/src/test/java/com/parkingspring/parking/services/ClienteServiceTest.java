package com.parkingspring.parking.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.parkingspring.parking.identities.Cliente;
import com.parkingspring.parking.repositories.ClienteRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryInterface clienteRepository; 

    @InjectMocks
    private ClienteService clienteService; 

    @Test
    void debeObtenerClientePorIdCorrectamente() {
        
        Integer idCliente = 1;
        Cliente clienteSimulado = Cliente.builder()
                .idCliente(idCliente)
                .documento("102030")
                .nombre("Santiago")
                .apellido("Palacio")
                .telefono("3000000")
                .email("santi@test.com")
                .build();

        when(clienteRepository.obtenerPorId(idCliente)).thenReturn(clienteSimulado);

        
        Cliente resultado = clienteService.obtenerPorId(idCliente);

        
        assertNotNull(resultado, "El cliente no debería ser nulo");
        assertEquals("Santiago", resultado.getNombre(), "El nombre no coincide");
        verify(clienteRepository, times(1)).obtenerPorId(idCliente);
    }
}