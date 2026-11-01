package com.parkingspring.parking.identities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {
    private String placa;
    private String marca;
    private String color;
    private String tipoVehiculo;
    private String documentoCliente;
}
