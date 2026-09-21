
CREATE DATABASE IF NOT EXISTS proyecto_spring;
USE proyecto_spring;
 
CREATE TABLE IF NOT EXISTS cliente (
    id_cliente  INT AUTO_INCREMENT PRIMARY KEY,
    documento   VARCHAR(20)  NOT NULL UNIQUE,
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    telefono    VARCHAR(20),
    email       VARCHAR(100)
);
 

INSERT INTO cliente (documento, nombre, apellido, telefono, email) VALUES
('1000111222', 'Juan', 'Perez', '3001234567', 'juan.perez@mail.com'),
('1000333444', 'Maria', 'Gomez', '3009876543', 'maria.gomez@mail.com');


SELECT * FROM cliente;

USE proyecto_spring;

SELECT * FROM cliente;

 SELECT * FROM cliente;
 
 
 USE proyecto_spring;

SELECT * FROM cliente
WHERE id_cliente = 7;

CREATE TABLE IF NOT EXISTS vehiculo (
    placa         VARCHAR(10) PRIMARY KEY,
    marca         VARCHAR(50),
    color         VARCHAR(30),
    tipo_vehiculo VARCHAR(20) NOT NULL,     
    documento_cliente VARCHAR(20),
    CONSTRAINT fk_vehiculo_cliente FOREIGN KEY (documento_cliente) REFERENCES cliente(documento)
);
 
CREATE TABLE IF NOT EXISTS espacio (
    id_espacio    INT AUTO_INCREMENT PRIMARY KEY,
    numero_espacio INT NOT NULL,
    nivel         INT NOT NULL,
    estado        BOOLEAN DEFAULT TRUE,       -
    tipo_espacio  VARCHAR(20)
);
 
CREATE TABLE IF NOT EXISTS tarifa (
    id_tarifa          INT AUTO_INCREMENT PRIMARY KEY,
    tipo_vehiculo       VARCHAR(20) NOT NULL UNIQUE,
    valor_hora          DECIMAL(10,2) NOT NULL,
    valor_dia           DECIMAL(10,2),
    valor_mensualidad   DECIMAL(10,2)
);
 
CREATE TABLE IF NOT EXISTS registro_parqueo_horas (
    id_registro         INT AUTO_INCREMENT PRIMARY KEY,
    placa               VARCHAR(10) NOT NULL,
    id_espacio          INT NOT NULL,
    fecha_hora_entrada  DATETIME NOT NULL,
    fecha_hora_salida   DATETIME NULL,
    tiempo_total        DECIMAL(10,2) NULL,   
    CONSTRAINT fk_registro_vehiculo FOREIGN KEY (placa) REFERENCES vehiculo(placa),
    CONSTRAINT fk_registro_espacio FOREIGN KEY (id_espacio) REFERENCES espacio(id_espacio)
);
 
CREATE TABLE IF NOT EXISTS factura (
    id_factura   INT AUTO_INCREMENT PRIMARY KEY,
    id_registro  INT NOT NULL,
    fecha_pago   DATETIME NOT NULL,
    valor_total  DECIMAL(10,2) NOT NULL,
    metodo_pago  VARCHAR(30),
    CONSTRAINT fk_factura_registro FOREIGN KEY (id_registro) REFERENCES registro_parqueo_horas(id_registro)
);
 
-- Datos de prueba
INSERT INTO tarifa (tipo_vehiculo, valor_hora, valor_dia, valor_mensualidad) VALUES
('carro', 3000, 20000, 150000),
('moto', 1500, 10000, 80000);
 
INSERT INTO espacio (numero_espacio, nivel, estado, tipo_espacio) VALUES
(1, 1, TRUE, 'carro'),
(2, 1, TRUE, 'moto');
 
INSERT INTO vehiculo (placa, marca, color, tipo_vehiculo, documento_cliente) VALUES
('ABC123', 'Mazda', 'Rojo', 'carro', '1000111222');



