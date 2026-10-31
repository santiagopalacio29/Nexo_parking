package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Tarifa;
import com.parkingspring.parking.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TarifaRepository implements TarifaRepositoryInterface{
    @Autowired
    private TarifaDAOHelper helper;

    @Autowired
    private Conexion conexion;

    public List<Tarifa> listarTarifas() {
        List<Tarifa> tarifas = new ArrayList<>();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.listarTarifas());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tarifas.add(mapearTarifa(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarifas;
    }

    public Tarifa obtenerTarifas(Integer idTarifa) {
        Tarifa tarifa = null;
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.obtenerTarifas())) {

            ps.setInt(1, idTarifa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tarifa = mapearTarifa(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarifa;
    }

    public Tarifa insertarTarifa(Tarifa tarifa) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.insertarTarifa(), Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, tarifa.getIdTarifa());
            ps.setString(2, tarifa.getTipoVehiculo());
            ps.setDouble(3, tarifa.getValorHora());
            ps.setDouble(4, tarifa.getValorDia());
            ps.setDouble(5, tarifa.getValorMensualidad());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    tarifa.setIdTarifa(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return tarifa;
    }

    public Tarifa actualizarTarifa(Tarifa tarifa) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.actualizarTarifa())) {

            ps.setInt(1, tarifa.getIdTarifa());
            ps.setString(2, tarifa.getTipoVehiculo());
            ps.setDouble(3, tarifa.getValorHora());
            ps.setDouble(4, tarifa.getValorDia());
            ps.setDouble(5, tarifa.getValorMensualidad());
            ps.setInt(6, tarifa.getIdTarifa());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return tarifa;
    }

    public boolean eliminarTarifa(Integer idTarifa) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.eliminarTarifa())) {
            ps.setInt(1, idTarifa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Tarifa mapearTarifa(ResultSet rs) throws SQLException {
        return Tarifa.builder()
                .idTarifa(rs.getInt("id_tarifa"))
                .tipoVehiculo(rs.getString("tipo_vehiculo"))
                .valorHora(rs.getDouble("valor_hora"))
                .valorDia(rs.getDouble("valor_dia"))
                .valorMensualidad(rs.getDouble("valor_mensualidad"))
                .build();
    }
}

