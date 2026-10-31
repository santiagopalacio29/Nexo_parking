package com.parkingspring.parking.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {
    private Integer idTarifa;
    private String tipoVehiculo;
    private Double valorHora;
    private Double valorDia;
    private Double valorMensualidad;
}
