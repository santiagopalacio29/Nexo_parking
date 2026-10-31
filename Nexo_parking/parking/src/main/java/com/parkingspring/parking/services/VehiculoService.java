package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Vehiculo;
import com.parkingspring.parking.repositories.VehiculoRepositoryInterface;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculoService implements VehiculoServiceInterface{

    private final VehiculoRepositoryInterface vehiculoRepository;

    public VehiculoService(VehiculoRepositoryInterface vehiculoRepository){
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> listarVehiculo() {
        return vehiculoRepository.listarVehiculo();
    }

    public Vehiculo obtenerVehiculo(String placa) {
        return vehiculoRepository.obtenerVehiculo(placa);
    }

    public Vehiculo insertarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.insertarVehiculo(vehiculo);
    }

    public Vehiculo actualizarVehiculo(String placa, Vehiculo vehiculo) {
        vehiculo.setPlaca(placa);
        return vehiculoRepository.actualizarVehiculo(vehiculo);
    }

    public boolean eliminarVehiculo(String placa) {
        return vehiculoRepository.eliminarVehiculo(placa);
    }
}

