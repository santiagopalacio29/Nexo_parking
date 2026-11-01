package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Espacio;
import com.parkingspring.parking.repositories.EspacioRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspacioService implements EspacioServiceInterface{
    private final EspacioRepositoryInterface espacioRepository;

    public EspacioService(EspacioRepositoryInterface espacioRepository){
        this.espacioRepository = espacioRepository;
    }

    public List<Espacio> listarEspacios() {
        return espacioRepository.listarEspacios();
    }

    public Espacio obtenerEspacios(Integer idEspacio) {
        return espacioRepository.obtenerEspacios(idEspacio);
    }

    public Espacio insertarEspacios(Espacio espacio) {
        return espacioRepository.insertarEspacios(espacio);
    }

    public Boolean actualizarEstado(Integer idEspacio, Espacio espacio) {
        espacio.setIdEspacio(idEspacio);
        return espacioRepository.actualizarEstado(espacio);
    }
}
