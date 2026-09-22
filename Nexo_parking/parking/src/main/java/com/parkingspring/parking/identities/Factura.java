package com.parkingspring.parking.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Factura {
    private Integer idFactura;
    private Integer idRegistro;
    private LocalDateTime fechaPago;
    private Double valorTotal;
    private String metodoPago;
}
