package com.parkingspring.parking.services;

import com.parkingspring.parking.identities.Tarifa;
import java.util.List;

public interface TarifaServiceInterface {
    List<Tarifa> listarTarifas();
    Tarifa obtenerTarifas(Integer idTarifa);
    Tarifa insertarTarifa(Tarifa tarifa);
    Tarifa actualizarTarifa(Integer idTarifa, Tarifa tarifa);
    boolean eliminarTarifa(Integer idTarifa);
}
