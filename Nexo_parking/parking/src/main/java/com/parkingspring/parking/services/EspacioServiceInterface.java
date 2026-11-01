package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Espacio;
import java.util.List;

public interface EspacioServiceInterface {
    List<Espacio> listarEspacios();
    Espacio obtenerEspacios(Integer idEspacio);
    Espacio insertarEspacios(Espacio espacio);
    Boolean actualizarEstado(Integer idEspacio, Espacio espacio);
}
