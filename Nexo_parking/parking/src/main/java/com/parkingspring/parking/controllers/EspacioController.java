package com.parkingspring.parking.controllers;

import com.parkingspring.parking.identities.Espacio;
import com.parkingspring.parking.services.EspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/espacio")
public class EspacioController {

        @Autowired
        private EspacioService espacioService;

        @GetMapping
        public ResponseEntity<List<Espacio>> listar() {
            try {
                return new ResponseEntity<>(espacioService.listarEspacios(), HttpStatus.OK);
            } catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Espacio> obtener(@PathVariable Integer idEspacio) {
            if (idEspacio == null || idEspacio <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            try {
                Espacio espacio = espacioService.obtenerEspacios(idEspacio);
                if (espacio == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                return new ResponseEntity<>(espacio, HttpStatus.OK);
            } catch (Exception exception) {
                exception.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PostMapping
        public ResponseEntity<Espacio> crear(@RequestBody Espacio espacio) {
            if (ObjectUtils.isEmpty(espacio) || espacio.getIdEspacio() == null || !espacio.getEstado()) {
                return new ResponseEntity<>(espacio, HttpStatus.BAD_REQUEST);
            }
            try {
                Espacio creado = espacioService.insertarEspacios(espacio);
                if (creado == null) {
                    return new ResponseEntity<>(espacio, HttpStatus.NOT_ACCEPTABLE);
                }
                return new ResponseEntity<>(creado, HttpStatus.CREATED);
            } catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(espacio, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<Espacio> actualizar(@PathVariable Integer id, @RequestBody Espacio espacio) {
            if (id == null || ObjectUtils.isEmpty(espacio)) {
                return new ResponseEntity<>(espacio, HttpStatus.BAD_REQUEST);
            }
            try {
                boolean actualizado = espacioService.actualizarEstado(id, espacio);
                if (!actualizado) {
                    return new ResponseEntity<>(espacio, HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(espacio, HttpStatus.OK);
            } catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(espacio, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}

