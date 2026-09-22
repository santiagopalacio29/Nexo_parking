package com.parkingspring.parking.repositories;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.identities.RegistroParqueo;
import com.parkingspring.parking.utilities.Conexion;

@Repository
public class ParqueoRepository {

    @Autowired
    private ParqueoDAOHelper helper;

    @Autowired
    private Conexion conexion;

    public List<RegistroParqueo> listar() {
        List<RegistroParqueo> registros = new ArrayList<>();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.listarRegistros());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                registros.add(mapearRegistro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public RegistroParqueo obtenerPorId(Integer id) {
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.obtenerRegistro())) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearRegistro(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   
    public RegistroParqueo registrarEntrada(String placa, Integer idEspacio) {
        Connection con = null;
        try {
            con = conexion.obtenerConexion();
            con.setAutoCommit(false);

            LocalDateTime ahora = LocalDateTime.now();

            RegistroParqueo registro;
            try (PreparedStatement ps = con.prepareStatement(helper.registrarEntrada(), Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, placa);
                ps.setInt(2, idEspacio);
                ps.setTimestamp(3, Timestamp.valueOf(ahora));
                ps.executeUpdate();

                Integer idRegistro = null;
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        idRegistro = keys.getInt(1);
                    }
                }

                registro = RegistroParqueo.builder()
                        .idRegistro(idRegistro)
                        .placa(placa)
                        .idEspacio(idEspacio)
                        .fechaHoraEntrada(ahora)
                        .build();
            }

            try (PreparedStatement ps = con.prepareStatement(helper.ocuparEspacio())) {
                ps.setInt(1, idEspacio);
                ps.executeUpdate();
            }

            con.commit();
            return registro;

        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            return null;
        } finally {
            cerrar(con);
        }
    }

   
    public Factura registrarSalida(Integer idRegistro, String metodoPago) {
        Connection con = null;
        try {
            con = conexion.obtenerConexion();
            con.setAutoCommit(false);

            RegistroParqueo registro = obtenerRegistroParaActualizar(con, idRegistro);
            if (registro == null || registro.getFechaHoraSalida() != null) {
                con.rollback();
                return null; // no existe o ya tiene salida registrada
            }

            LocalDateTime ahora = LocalDateTime.now();
            double horas = Duration.between(registro.getFechaHoraEntrada(), ahora).toMinutes() / 60.0;
            double horasCobradas = Math.max(1.0, Math.ceil(horas)); // mínimo 1 hora

            try (PreparedStatement ps = con.prepareStatement(helper.registrarSalida())) {
                ps.setTimestamp(1, Timestamp.valueOf(ahora));
                ps.setDouble(2, horasCobradas);
                ps.setInt(3, idRegistro);
                ps.executeUpdate();
            }

            BigDecimal valorHora;
            try (PreparedStatement ps = con.prepareStatement(helper.obtenerTarifaPorTipoVehiculo())) {
                ps.setInt(1, idRegistro);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        con.rollback();
                        return null; // no hay tarifa configurada para ese tipo de vehículo
                    }
                    valorHora = rs.getBigDecimal("valor_hora");
                }
            }

            BigDecimal valorTotal = valorHora
                    .multiply(BigDecimal.valueOf(horasCobradas))
                    .setScale(2, RoundingMode.HALF_UP);

            Factura factura = Factura.builder()
                    .idRegistro(idRegistro)
                    .fechaPago(ahora)
                    .valorTotal(valorTotal.doubleValue())
                    .metodoPago(metodoPago)
                    .build();

            try (PreparedStatement ps = con.prepareStatement(helper.insertarFactura(), Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idRegistro);
                ps.setTimestamp(2, Timestamp.valueOf(ahora));
                ps.setBigDecimal(3, valorTotal);
                ps.setString(4, metodoPago);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        factura.setIdFactura(keys.getInt(1));
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(helper.liberarEspacio())) {
                ps.setInt(1, registro.getIdEspacio());
                ps.executeUpdate();
            }

            con.commit();
            return factura;

        } catch (SQLException e) {
            e.printStackTrace();
            rollback(con);
            return null;
        } finally {
            cerrar(con);
        }
    }

    private RegistroParqueo obtenerRegistroParaActualizar(Connection con, Integer id) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(helper.obtenerRegistro())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearRegistro(rs);
                }
            }
        }
        return null;
    }

    private RegistroParqueo mapearRegistro(ResultSet rs) throws SQLException {
        Timestamp salida = rs.getTimestamp("fecha_hora_salida");
        return RegistroParqueo.builder()
                .idRegistro(rs.getInt("id_registro"))
                .placa(rs.getString("placa"))
                .idEspacio(rs.getInt("id_espacio"))
                .fechaHoraEntrada(rs.getTimestamp("fecha_hora_entrada").toLocalDateTime())
                .fechaHoraSalida(salida != null ? salida.toLocalDateTime() : null)
                .tiempoTotal(rs.getObject("tiempo_total") != null ? rs.getDouble("tiempo_total") : null)
                .build();
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    private void cerrar(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
