package com.parkingspring.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // GET
    @GetMapping
    public ResponseEntity<List<Factura>> listar() {
        return ResponseEntity.ok(facturaService.listarFactura());
    }

    // GET
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtener(@PathVariable Integer id) {
        Factura factura = facturaService.obtenerFactura(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factura);
    }

    // POST
    @PostMapping
    public ResponseEntity<Factura> crear(@RequestBody Factura factura) {
        Factura creado = facturaService.crearFactura(factura);
        if (creado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizar(@PathVariable Integer id, @RequestBody Factura factura) {
        Factura actualizado = facturaService.actualizarFactura(id, factura);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        boolean eliminado = facturaService.eliminarFactura(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

