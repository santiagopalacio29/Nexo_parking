package com.parkingspring.parking.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.parkingspring.parking.identities.Vehiculo;
import com.parkingspring.parking.repositories.VehiculoRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepositoryInterface vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    void debeObtenerVehiculoPorPlacaCorrectamente() {
        
        String placa = "ABC123";
        Vehiculo vehiculoSimulado = Vehiculo.builder()
                .placa(placa)
                .marca("Mazda")
                .color("Rojo")
                .tipoVehiculo("CARRO")
                .documentoCliente("102030")
                .build();

        
        when(vehiculoRepository.obtenerVehiculo(placa)).thenReturn(vehiculoSimulado);

        
        Vehiculo resultado = vehiculoService.obtenerVehiculo(placa);

        
        assertNotNull(resultado, "El vehículo no debería ser nulo");
        assertEquals(placa, resultado.getPlaca(), "La placa no coincide");
        assertEquals("Mazda", resultado.getMarca(), "La marca no coincide");
        verify(vehiculoRepository, times(1)).obtenerVehiculo(placa);
    }
}