package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.identities.RegistroParqueo;

import java.util.List;

public interface ParqueoServiceInterface {
    List<RegistroParqueo> listarRegistros();
    RegistroParqueo obtenerRegistro(Integer id);
    RegistroParqueo registrarEntrada(String placa, Integer idEspacio);
    Factura registrarSalida(Integer idRegistro, String metodoPago);
}
