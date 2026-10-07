
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
	estado        BOOLEAN DEFAULT TRUE,       
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

INSERT INTO factura (id_factura, id_registro, fecha_pago, valor_total, metodo_pago) VALUES
(12345678, 1234, '2000-10-10 14:30:00', 1234567, 'efectivo');

-- 1. Limpiamos por si acaso hay datos corruptos o vacíos
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM factura;
DELETE FROM registro_parqueo_horas;
DELETE FROM vehiculo;
DELETE FROM espacio;
DELETE FROM tarifa;
DELETE FROM cliente;
SET FOREIGN_KEY_CHECKS = 1;

-- 2. Insertamos Clientes
INSERT INTO cliente (documento, nombre, apellido, telefono, email) VALUES
('1000111222', 'Juan', 'Perez', '3001234567', 'juan.perez@mail.com'),
('1000333444', 'Maria', 'Gomez', '3009876543', 'maria.gomez@mail.com');

-- 3. Insertamos Tarifas
INSERT INTO tarifa (tipo_vehiculo, valor_hora, valor_dia, valor_mensualidad) VALUES
('carro', 3000, 20000, 150000),
('moto', 1500, 10000, 80000);

-- 4. Insertamos Espacios
INSERT INTO espacio (numero_espacio, nivel, estado, tipo_espacio) VALUES
(1, 1, TRUE, 'carro'),
(2, 1, TRUE, 'moto');

-- 5. Insertamos Vehículos (depende del cliente)
INSERT INTO vehiculo (placa, marca, color, tipo_vehiculo, documento_cliente) VALUES
('ABC123', 'Mazda', 'Rojo', 'carro', '1000111222');

-- 6. Insertamos el Registro de Parqueo (¡ESTE FALTABA!)
-- Como id_registro es auto_increment, este será el id 1.
INSERT INTO registro_parqueo_horas (placa, id_espacio, fecha_hora_entrada, fecha_hora_salida, tiempo_total) 
VALUES ('ABC123', 1, '2000-10-10 12:00:00', '2000-10-10 14:30:00', 2.5);

-- 7. Ahora sí insertamos la Factura (apuntando al id_registro = 1 que acabamos de crear)
INSERT INTO factura (id_registro, fecha_pago, valor_total, metodo_pago) 
VALUES (1, '2000-10-10 14:30:00', 1234567, 'efectivo');

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE factura;
TRUNCATE TABLE registro_parqueo_horas;
TRUNCATE TABLE vehiculo;
TRUNCATE TABLE espacio;
TRUNCATE TABLE tarifa;
TRUNCATE TABLE cliente;
SET FOREIGN_KEY_CHECKS = 1;

-- 2. Insertamos Clientes primero
INSERT INTO cliente (documento, nombre, apellido, telefono, email) VALUES
('1000111222', 'Juan', 'Perez', '3001234567', 'juan.perez@mail.com'),
('1000333444', 'Maria', 'Gomez', '3009876543', 'maria.gomez@mail.com');

-- 3. Insertamos Tarifas
INSERT INTO tarifa (tipo_vehiculo, valor_hora, valor_dia, valor_mensualidad) VALUES
('carro', 3000, 20000, 150000),
('moto', 1500, 10000, 80000);

-- 4. Insertamos Espacios (Este generará el id_espacio = 1)
INSERT INTO espacio (numero_espacio, nivel, estado, tipo_espacio) VALUES
(1, 1, TRUE, 'carro'),
(2, 1, TRUE, 'moto');

-- 5. Insertamos Vehículos (depende del cliente)
INSERT INTO vehiculo (placa, marca, color, tipo_vehiculo, documento_cliente) VALUES
('ABC123', 'Mazda', 'Rojo', 'carro', '1000111222');

-- 6. Insertamos el Registro de Parqueo (depende del vehículo y del espacio con id_espacio = 1)
INSERT INTO registro_parqueo_horas (placa, id_espacio, fecha_hora_entrada, fecha_hora_salida, tiempo_total) 
VALUES ('ABC123', 1, '2000-10-10 12:00:00', '2000-10-10 14:30:00', 2.5);

-- 7. Insertamos la Factura (depende del id_registro que se acaba de crear, que será el 1)
INSERT INTO factura (id_registro, fecha_pago, valor_total, metodo_pago) 
VALUES (1, '2000-10-10 14:30:00', 1234567, 'efectivo');

-- 8. Verificamos que todo se haya guardado correctamente
SELECT * FROM factura;

