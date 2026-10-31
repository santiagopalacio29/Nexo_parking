package com.parkingspring.parking.repositories;

import com.parkingspring.parking.identities.Vehiculo;
import com.parkingspring.parking.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehiculoRepository implements VehiculoRepositoryInterface{
    @Autowired
    private VehiculoDAOHelper helper;

    @Autowired
    private Conexion conexion;

    public List<Vehiculo> listarVehiculo() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.listarVehiculo());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vehiculos.add(mapearVehiculo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    public Vehiculo obtenerVehiculo(String placa) {
        Vehiculo vehiculo = null;
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.obtenerVehiculo())) {

            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehiculo = mapearVehiculo(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    public Vehiculo insertarVehiculo(Vehiculo vehiculo) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.insertarVehiculo(), Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getColor());
            ps.setString(4, vehiculo.getTipoVehiculo());
            ps.setString(5, vehiculo.getDocumentoCliente());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    vehiculo.setPlaca(keys.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vehiculo;
    }

    public Vehiculo actualizarVehiculo(Vehiculo vehiculo) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.actualizarVehiculo())) {

            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getColor());
            ps.setString(4, vehiculo.getTipoVehiculo());
            ps.setString(5, vehiculo.getDocumentoCliente());
            ps.setString(6, vehiculo.getPlaca());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vehiculo;
    }

    public boolean eliminarVehiculo(String placa) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.eliminarVehiculo())) {

            ps.setString(1, placa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        return Vehiculo.builder()
                .placa(rs.getString("placa"))
                .marca(rs.getString("marca"))
                .color(rs.getString("color"))
                .tipoVehiculo(rs.getString("tipo_vehiculo"))
                .documentoCliente(rs.getString("documento_cliente"))
                .build();
    }
}
