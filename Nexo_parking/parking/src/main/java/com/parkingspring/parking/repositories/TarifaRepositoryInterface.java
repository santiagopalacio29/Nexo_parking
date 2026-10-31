package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Tarifa;
import java.util.List;

public interface TarifaRepositoryInterface {
    List<Tarifa> listarTarifas();
    Tarifa obtenerTarifas(Integer idTarifa);
    Tarifa insertarTarifa(Tarifa tarifa);
    Tarifa actualizarTarifa(Tarifa tarifa);
    boolean eliminarTarifa(Integer idTarifa);
}
