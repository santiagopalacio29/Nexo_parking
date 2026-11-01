package com.parkingspring.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingspring.parking.identities.Cliente;
import com.parkingspring.parking.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para la gestión de clientes del parqueadero")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes", description = "Obtiene una lista completa de todos los clientes registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        try {
            return new ResponseEntity<>(clienteService.listarCliente(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener un cliente por ID", description = "Devuelve los datos de un cliente específico basado en su identificador único.")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "400", description = "ID de cliente inválido")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Cliente cliente = clienteService.obtenerPorId(id);
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Registrar un nuevo cliente", description = "Crea un nuevo registro de cliente en la base de datos.")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de cliente incompletos o inválidos")
    @ApiResponse(responseCode = "406", description = "No se pudo registrar el cliente")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        if (ObjectUtils.isEmpty(cliente) || ObjectUtils.isEmpty(cliente.getDocumento())
                || ObjectUtils.isEmpty(cliente.getNombre()) || ObjectUtils.isEmpty(cliente.getApellido())) {
            return new ResponseEntity<>(cliente, HttpStatus.BAD_REQUEST);
        }
        try {
            Cliente creado = clienteService.crearCliente(cliente);
            if (creado == null) {
                return new ResponseEntity<>(cliente, HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(cliente, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar un cliente existente", description = "Modifica los datos de un cliente basado en su ID.")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        if (id == null || id <= 0 || ObjectUtils.isEmpty(cliente) || ObjectUtils.isEmpty(cliente.getDocumento())) {
            return new ResponseEntity<>(cliente, HttpStatus.BAD_REQUEST);
        }
        try {
            Cliente actualizado = clienteService.actualizarCliente(id, cliente);
            if (actualizado == null) {
                return new ResponseEntity<>(cliente, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(cliente, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar un cliente", description = "Borra el registro de un cliente del sistema utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente (Sin contenido)")
    @ApiResponse(responseCode = "400", description = "ID de cliente inválido")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            boolean eliminado = clienteService.eliminarCliente(id);
            if (!eliminado) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}