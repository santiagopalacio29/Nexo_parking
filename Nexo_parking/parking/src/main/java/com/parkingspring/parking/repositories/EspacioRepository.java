package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Espacio;
import com.parkingspring.parking.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EspacioRepository implements EspacioRepositoryInterface{
    @Autowired
    private EspacioDAOHelper helper;

    @Autowired
    private Conexion conexion;

    public List<Espacio> listarEspacios() {
        List<Espacio> espacios = new ArrayList<>();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.listarEspacios());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                espacios.add(mapearEspacio(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return espacios;
    }

    public Espacio obtenerEspacios(Integer idEspacio) {
        Espacio espacio = null;
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.obtenerEspacios())) {

            ps.setInt(1, idEspacio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    espacio = mapearEspacio(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return espacio;
    }

    public Espacio insertarEspacios(Espacio espacio) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.insertarEspacios(), Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, espacio.getIdEspacio());
            ps.setInt(2, espacio.getNumeroEspacio());
            ps.setInt(3, espacio.getNivel());
            ps.setBoolean(4, espacio.getEstado());
            ps.setString(5, espacio.getTipoEspacio());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    espacio.setIdEspacio(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return espacio;
    }

    public Boolean actualizarEstado(Espacio espacio) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.actualizarEstado())) {

            ps.setBoolean(1, espacio.getEstado());
            ps.setInt(2, espacio.getIdEspacio());

            int filas = ps.executeUpdate();
            return filas>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Espacio mapearEspacio(ResultSet rs) throws SQLException {
        return Espacio.builder()
                .idEspacio(rs.getInt("id_espacio"))
                .numeroEspacio(rs.getInt("numero_espacio"))
                .nivel(rs.getInt("nivel"))
                .estado(rs.getBoolean("estado"))
                .tipoEspacio(rs.getString("tipo_espacio"))
                .build();
    }
}
