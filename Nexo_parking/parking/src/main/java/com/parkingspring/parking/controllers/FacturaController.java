package com.parkingspring.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.services.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/factura")
@Tag(name = "Facturas", description = "Endpoints para la gestión de facturación y cobros del parqueadero")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Operation(summary = "Listar todas las facturas", description = "Obtiene el historial completo de facturas emitidas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Factura>> listar() {
        try {
            return new ResponseEntity<>(facturaService.listarFactura(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Consultar factura por ID", description = "Busca y retorna los detalles de una factura específica.")
    @ApiResponse(responseCode = "200", description = "Factura encontrada")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtener(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Factura factura = facturaService.obtenerFactura(id);
            if (factura == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Crear factura manualmente", description = "Registra una nueva factura en el sistema.")
    @ApiResponse(responseCode = "201", description = "Factura creada con éxito")
    @PostMapping
    public ResponseEntity<Factura> crear(@RequestBody Factura factura) {
        if (ObjectUtils.isEmpty(factura) || factura.getIdRegistro() == null || factura.getValorTotal() == null) {
            return new ResponseEntity<>(factura, HttpStatus.BAD_REQUEST);
        }
        try {
            Factura creado = facturaService.insertarFactura(factura);
            if (creado == null) {
                return new ResponseEntity<>(factura, HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(factura, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar factura", description = "Modifica los datos de una factura existente.")
    @ApiResponse(responseCode = "200", description = "Factura actualizada")
    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizar(@PathVariable Integer id, @RequestBody Factura factura) {
        if (id == null || id <= 0 || ObjectUtils.isEmpty(factura)) {
            return new ResponseEntity<>(factura, HttpStatus.BAD_REQUEST);
        }
        try {
            Factura actualizado = facturaService.actualizarFactura(id, factura);
            if (actualizado == null) {
                return new ResponseEntity<>(factura, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(factura, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar factura", description = "Elimina una factura del sistema mediante su ID.")
    @ApiResponse(responseCode = "204", description = "Factura eliminada (Sin contenido)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            boolean eliminado = facturaService.eliminarFactura(id);
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