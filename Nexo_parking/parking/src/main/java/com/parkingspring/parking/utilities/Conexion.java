package com.parkingspring.parking.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Centraliza la obtención de conexiones JDBC.
 * Usa el DataSource que Spring Boot configura automáticamente
 * a partir de spring.datasource.* en application.properties.
 */
@Component
public class Conexion {

    @Autowired
    private DataSource dataSource;

    public Connection obtenerConexion() throws SQLException {
        return dataSource.getConnection();
    }
}
