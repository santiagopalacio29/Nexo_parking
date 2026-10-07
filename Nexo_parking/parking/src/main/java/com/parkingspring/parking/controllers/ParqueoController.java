package com.parkingspring.parking.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingspring.parking.identities.Factura;
import com.parkingspring.parking.identities.RegistroParqueo;
import com.parkingspring.parking.services.ParqueoService;

@RestController
@RequestMapping("/api/parqueo")
public class ParqueoController {

    @Autowired
    private ParqueoService parqueoService;

    @GetMapping
    public ResponseEntity<List<RegistroParqueo>> listar() {
        try {
            return new ResponseEntity<>(parqueoService.listarRegistros(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroParqueo> obtener(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            RegistroParqueo registro = parqueoService.obtenerRegistro(id);
            if (registro == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return new ResponseEntity<>(registro, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/entrada")
    public ResponseEntity<RegistroParqueo> registrarEntrada(@RequestBody Map<String, Object> body) {
        String placa = (String) body.get("placa");
        Integer idEspacio = (Integer) body.get("idEspacio");

        if (ObjectUtils.isEmpty(placa) || idEspacio == null || idEspacio <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            RegistroParqueo registro = parqueoService.registrarEntrada(placa, idEspacio);
            if (registro == null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
            return new ResponseEntity<>(registro, HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/salida")
    public ResponseEntity<Factura> registrarSalida(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        String metodoPago = (String) body.get("metodoPago");

        if (id == null || id <= 0 || ObjectUtils.isEmpty(metodoPago)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Factura factura = parqueoService.registrarSalida(id, metodoPago);
            if (factura == null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}