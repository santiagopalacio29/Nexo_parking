package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Vehiculo;

import java.util.List;

public interface VehiculoRepositoryInterface {
    List<Vehiculo> listarVehiculo();
    Vehiculo obtenerVehiculo(String placa);
    Vehiculo insertarVehiculo(Vehiculo vehiculo);
    Vehiculo actualizarVehiculo(Vehiculo vehiculo);
    boolean eliminarVehiculo(String placa);
}
