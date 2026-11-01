package com.parkingspring.parking.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.parkingspring.parking.identities.Espacio;
import com.parkingspring.parking.repositories.EspacioRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EspacioServiceTest {

    @Mock
    private EspacioRepositoryInterface espacioRepository;

    @InjectMocks
    private EspacioService espacioService;

    @Test
    void debeObtenerEspacioPorIdCorrectamente() {
      
        Integer idEspacio = 1;
        Espacio espacioSimulado = Espacio.builder()
                .idEspacio(idEspacio)
                .numeroEspacio(101)
                .nivel(1)
                .estado(true)
                .tipoEspacio("CARRO")
                .build();

        
        when(espacioRepository.obtenerEspacios(idEspacio)).thenReturn(espacioSimulado);

        
        Espacio resultado = espacioService.obtenerEspacios(idEspacio);

        
        assertNotNull(resultado, "El espacio no debería ser nulo");
        assertEquals(idEspacio, resultado.getIdEspacio(), "El ID no coincide");
        assertEquals("CARRO", resultado.getTipoEspacio(), "El tipo de espacio no coincide");
        verify(espacioRepository, times(1)).obtenerEspacios(idEspacio);
    }
}