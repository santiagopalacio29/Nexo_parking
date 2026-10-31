package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Espacio;

import java.util.List;

public interface EspacioRepositoryInterface {
    List<Espacio> listarEspacios();
    Espacio obtenerEspacios(Integer idEspacio);
    Espacio insertarEspacios(Espacio espacio);
    Boolean actualizarEstado(Espacio espacio);
}
