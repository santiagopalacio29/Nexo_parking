package com.parkingspring.parking.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.parkingspring.parking.identities.RegistroParqueo;
import com.parkingspring.parking.repositories.ParqueoRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class ParqueoServiceTest {

    @Mock
    private ParqueoRepositoryInterface parqueoRepository;

    @InjectMocks
    private ParqueoService parqueoService;

    @Test
    void debeObtenerRegistroPorIdCorrectamente() {
        // 1. ARRANGE (Preparación)
        Integer idRegistro = 25;
        RegistroParqueo registroSimulado = RegistroParqueo.builder()
                .idRegistro(idRegistro)
                .placa("XYZ789")
                .idEspacio(5)
                .fechaHoraEntrada(LocalDateTime.now())
                .build();

        when(parqueoRepository.obtenerRegistro(idRegistro)).thenReturn(registroSimulado);

        // 2. ACT (Ejecución)
        RegistroParqueo resultado = parqueoService.obtenerRegistro(idRegistro);

        // 3. ASSERT (Verificación)
        assertNotNull(resultado, "El registro no debería ser nulo");
        assertEquals("XYZ789", resultado.getPlaca(), "La placa no coincide");
        assertEquals(5, resultado.getIdEspacio(), "El espacio no coincide");
        verify(parqueoRepository, times(1)).obtenerRegistro(idRegistro);
    }
}