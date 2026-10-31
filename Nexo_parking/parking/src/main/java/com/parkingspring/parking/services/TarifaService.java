package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Tarifa;
import com.parkingspring.parking.repositories.TarifaRepositoryInterface;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TarifaService implements TarifaServiceInterface{
    private final TarifaRepositoryInterface tarifaRepository;

    public TarifaService(TarifaRepositoryInterface tarifaRepository){
        this.tarifaRepository = tarifaRepository;
    }

    public List<Tarifa> listarTarifas() {
        return tarifaRepository.listarTarifas();
    }

    public Tarifa obtenerTarifas(Integer idTarifa) {
        return tarifaRepository.obtenerTarifas(idTarifa);
    }

    public Tarifa insertarTarifa(Tarifa tarifa) {
        return tarifaRepository.insertarTarifa(tarifa);
    }

    public Tarifa actualizarTarifa(Integer idTarifa, Tarifa tarifa) {
        tarifa.setIdTarifa(idTarifa);
        return tarifaRepository.actualizarTarifa(tarifa);
    }

    public boolean eliminarTarifa(Integer idTarifa) {
        return tarifaRepository.eliminarTarifa(idTarifa);
    }
}

