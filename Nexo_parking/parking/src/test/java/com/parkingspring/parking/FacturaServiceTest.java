package com.parkingspring.parking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.identities.MetodoPago;
import com.parkingspring.parking.repositories.FacturaRepositoryInterface;
import com.parkingspring.parking.services.FacturaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepositoryInterface facturaRepository; 

    @InjectMocks
    private FacturaService facturaService; 

    @Test
    void debeObtenerFacturaPorIdCorrectamente() {
        
        Integer idFactura = 100;
        Factura facturaSimulada = Factura.builder()
                .idFactura(idFactura)
                .idRegistro(10)
                .fechaPago(LocalDateTime.now())
                .valorTotal(5000.0)
                .metodoPago(MetodoPago.EFECTIVO)
                .build();
        
        when(facturaRepository.obtenerFactura(idFactura)).thenReturn(facturaSimulada);

        
        Factura resultado = facturaService.obtenerFactura(idFactura);

        
        assertNotNull(resultado, "La factura no debería ser nula");
        assertEquals(5000.0, resultado.getValorTotal(), "El valor total no coincide");
        assertEquals(MetodoPago.EFECTIVO, resultado.getMetodoPago(), "El método de pago no coincide");
        
        verify(facturaRepository, times(1)).obtenerFactura(idFactura); 
    }
}