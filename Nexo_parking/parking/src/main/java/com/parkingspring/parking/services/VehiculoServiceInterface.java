package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Vehiculo;
import java.util.List;

public interface VehiculoServiceInterface {
    List<Vehiculo> listarVehiculo();
    Vehiculo obtenerVehiculo(String placa);
    Vehiculo insertarVehiculo(Vehiculo vehiculo);
    Vehiculo actualizarVehiculo(String placa, Vehiculo vehiculo);
    boolean eliminarVehiculo(String placa);
}
