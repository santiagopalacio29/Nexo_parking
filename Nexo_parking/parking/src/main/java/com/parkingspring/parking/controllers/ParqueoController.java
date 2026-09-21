package com.parkingspring.parking.controllers;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.identities.RegistroParqueo;
import com.parkingspring.parking.services.ParqueoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parqueo")
public class ParqueoController {

    @Autowired
    private ParqueoService parqueoService;

    // GET /api/parqueo -> listar todos los registros
    @GetMapping
    public ResponseEntity<List<RegistroParqueo>> listar() {
        return ResponseEntity.ok(parqueoService.listarRegistros());
    }

    // GET /api/parqueo/{id} -> consultar un registro
    @GetMapping("/{id}")
    public ResponseEntity<RegistroParqueo> obtener(@PathVariable Integer id) {
        RegistroParqueo registro = parqueoService.obtenerRegistro(id);
        if (registro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    // POST /api/parqueo/entrada  body: {"placa":"ABC123","idEspacio":1}
    @PostMapping("/entrada")
    public ResponseEntity<RegistroParqueo> registrarEntrada(@RequestBody Map<String, Object> body) {
        String placa = (String) body.get("placa");
        Integer idEspacio = (Integer) body.get("idEspacio");

        RegistroParqueo registro = parqueoService.registrarEntrada(placa, idEspacio);
        if (registro == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    // PUT /api/parqueo/{id}/salida  body: {"metodoPago":"efectivo"}
    @PutMapping("/{id}/salida")
    public ResponseEntity<Factura> registrarSalida(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        String metodoPago = (String) body.get("metodoPago");

        Factura factura = parqueoService.registrarSalida(id, metodoPago);
        if (factura == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(factura);
    }
}
