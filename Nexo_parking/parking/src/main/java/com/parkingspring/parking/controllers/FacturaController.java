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

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.services.FacturaService;

@RestController
@RequestMapping("/api/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> listar() {
        try {
            return new ResponseEntity<>(facturaService.listarFactura(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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