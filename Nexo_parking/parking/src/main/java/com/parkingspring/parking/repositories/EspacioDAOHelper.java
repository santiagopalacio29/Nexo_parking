package com.parkingspring.parking.repositories;

import org.springframework.stereotype.Component;

@Component
public class EspacioDAOHelper {

    public String listarEspacios() {
        return "SELECT id_espacio, numero_espacio, nivel, estado, tipo_espacio FROM espacio";
    }

    public String obtenerEspacios() {
        return "SELECT id_espacio, numero_espacio, nivel, estado, tipo_espacio FROM espacio WHERE id_espacio = ?";
    }

    public String insertarEspacios() {
        return "INSERT INTO espacio (id_espacio, numero_espacio, nivel, estado, tipo_espacio) VALUES (?, ?, ?, ?, ?)";
    }

    public String actualizarEstado() {
        return "UPDATE espacio SET estado = ? WHERE id_espacio = ?";
    }
}
