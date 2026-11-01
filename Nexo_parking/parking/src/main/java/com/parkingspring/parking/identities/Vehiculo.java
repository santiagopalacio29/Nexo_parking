package com.parkingspring.parking.identities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {

    @Id
    private String placa;

    private String marca;
    private String color;

    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;

    // Relación ManyToOne requerida por la rúbrica: Muchos vehículos pertenecen a un cliente
    @ManyToOne
    @JoinColumn(name = "documento_cliente", referencedColumnName = "documento")
    private Cliente cliente;
}