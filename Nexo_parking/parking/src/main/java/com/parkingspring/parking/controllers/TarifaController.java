package com.parkingspring.parking.controllers;

import com.parkingspring.parking.identities.Tarifa;
import com.parkingspring.parking.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarifa")
public class TarifaController {

        @Autowired
        private TarifaService tarifaService;

        @GetMapping
        public ResponseEntity<List<Tarifa>> listar() {
            try {
                return new ResponseEntity<>(tarifaService.listarTarifas(), HttpStatus.OK);
            } catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Tarifa> obtener(@PathVariable Integer id) {
            if (id == null || id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            try {
                Tarifa tarifa = tarifaService.obtenerTarifas(id);
                if (tarifa == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                return new ResponseEntity<>(tarifa, HttpStatus.OK);
            } catch (Exception exception) {
                exception.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PostMapping
        public ResponseEntity<Tarifa> crear(@RequestBody Tarifa tarifa) {
            if (ObjectUtils.isEmpty(tarifa) || tarifa.getIdTarifa() == null || tarifa.getTipoVehiculo() == null) {
                return new ResponseEntity<>(tarifa, HttpStatus.BAD_REQUEST);
            }
            try {
                Tarifa creado = tarifaService.insertarTarifa(tarifa);
                if (creado == null) {
                    return new ResponseEntity<>(tarifa, HttpStatus.NOT_ACCEPTABLE);
                }
                return new ResponseEntity<>(creado, HttpStatus.CREATED);
            } catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(tarifa, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<Tarifa> actualizar(@PathVariable Integer id, @RequestBody Tarifa tarifa) {
            if (id == null || id <= 0 || ObjectUtils.isEmpty(tarifa)) {
                return new ResponseEntity<>(tarifa, HttpStatus.BAD_REQUEST);
            }
            try {
                Tarifa actualizado = tarifaService.actualizarTarifa(id, tarifa);
                if (actualizado == null) {
                    return new ResponseEntity<>(tarifa, HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(actualizado, HttpStatus.OK);
            } catch (Exception exception) {
                exception.printStackTrace();
                return new ResponseEntity<>(tarifa, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
            if (id == null || id <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            try {
                boolean eliminado = tarifaService.eliminarTarifa(id);
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
