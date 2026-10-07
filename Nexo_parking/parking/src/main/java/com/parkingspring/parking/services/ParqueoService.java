package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.identities.RegistroParqueo;
import com.parkingspring.parking.repositories.ParqueoRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParqueoService implements ParqueoServiceInterface{

    private final ParqueoRepositoryInterface parqueoRepository;

    public ParqueoService(ParqueoRepositoryInterface parqueoRepository){
        this.parqueoRepository = parqueoRepository;
    }

    public List<RegistroParqueo> listarRegistros() {
        return parqueoRepository.listarRegistros();
    }

    public RegistroParqueo obtenerRegistro(Integer id) {
        return parqueoRepository.obtenerRegistro(id);
    }

    public RegistroParqueo registrarEntrada(String placa, Integer idEspacio) {
        return parqueoRepository.registrarEntrada(placa, idEspacio);
    }

    public Factura registrarSalida(Integer idRegistro, String metodoPago) {
        return parqueoRepository.registrarSalida(idRegistro, metodoPago);
    }
}
