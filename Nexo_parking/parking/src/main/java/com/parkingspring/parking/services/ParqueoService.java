package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.identities.RegistroParqueo;
import com.parkingspring.parking.repositories.ParqueoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParqueoService {

    @Autowired
    private ParqueoRepository parqueoRepository;

    public List<RegistroParqueo> listarRegistros() {
        return parqueoRepository.listar();
    }

    public RegistroParqueo obtenerRegistro(Integer id) {
        return parqueoRepository.obtenerPorId(id);
    }

    public RegistroParqueo registrarEntrada(String placa, Integer idEspacio) {
        return parqueoRepository.registrarEntrada(placa, idEspacio);
    }

    public Factura registrarSalida(Integer idRegistro, String metodoPago) {
        return parqueoRepository.registrarSalida(idRegistro, metodoPago);
    }
}
