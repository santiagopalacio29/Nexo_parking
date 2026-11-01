package com.parkingspring.parking.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.parkingspring.parking.identities.Tarifa;
import com.parkingspring.parking.repositories.TarifaRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TarifaServiceTest {

    @Mock
    private TarifaRepositoryInterface tarifaRepository;

    @InjectMocks
    private TarifaService tarifaService;

    @Test
    void debeObtenerTarifaPorIdCorrectamente() {
        
        Integer idTarifa = 1;
        Tarifa tarifaSimulada = Tarifa.builder()
                .idTarifa(idTarifa)
                .tipoVehiculo("CARRO")
                .valorHora(5000.0)
                .valorDia(35000.0)
                .valorMensualidad(200000.0)
                .build();

        // Asegúrate de que el método en tu TarifaService se llame obtenerTarifas o obtenerPorId según corresponda
        when(tarifaRepository.obtenerTarifas(idTarifa)).thenReturn(tarifaSimulada);

       
        Tarifa resultado = tarifaService.obtenerTarifas(idTarifa);

        
        assertNotNull(resultado, "La tarifa no debería ser nula");
        assertEquals("CARRO", resultado.getTipoVehiculo(), "El tipo de vehículo no coincide");
        assertEquals(5000.0, resultado.getValorHora(), "El valor de la hora no coincide");
        verify(tarifaRepository, times(1)).obtenerTarifas(idTarifa);
    }
}