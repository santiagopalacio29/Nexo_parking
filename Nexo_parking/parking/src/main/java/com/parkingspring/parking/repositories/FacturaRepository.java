package com.parkingspring.parking.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.utilities.Conexion;

@Repository
public class FacturaRepository {

    @Autowired
    private FacturaDAOHelper helper;

    @Autowired
    private Conexion conexion;

    public List<Factura> listar() {
        List<Factura> facturas = new ArrayList<>();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.listarFacturas());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                facturas.add(mapearFactura(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }

    public Factura obtenerPorId(Integer id) {
        Factura factura = null;
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.obtenerFactura())) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    factura = mapearFactura(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factura;
    }

    public Factura insertar(Factura factura) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.insertarFactura(), Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdRegistro());
            ps.setObject(3, factura.getFechaPago());
            ps.setDouble(4, factura.getValorTotal());
            ps.setString(5, factura.getMetodoPago());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    factura.setIdFactura(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return factura;
    }

    public Factura actualizar(Factura factura) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.actualizarFactura())) {

            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdRegistro());
            ps.setObject(3, factura.getFechaPago());
            ps.setDouble(4, factura.getValorTotal());
            ps.setString(5, factura.getMetodoPago());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return factura;
    }

    public boolean eliminar(Integer id) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.eliminarFactura())) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Factura mapearFactura(ResultSet rs) throws SQLException {
        return Factura.builder()
                .idFactura(rs.getInt("id_factura"))
                .idRegistro(rs.getInt("id_registro"))
                .fechaPago(rs.getObject("fecha_pago", LocalDateTime.class))
                .valorTotal(rs.getDouble("valor_total"))
                .metodoPago(rs.getString("metodo_pago"))
                .build();
    }
}

