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

import com.parkingspring.parking.identities.Vehiculo;
import com.parkingspring.parking.services.VehiculoService;

@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar() {
        try {
            return new ResponseEntity<>(vehiculoService.listarVehiculo(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Vehiculo> obtener(@PathVariable String placa) {
        if (placa == null || placa.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Vehiculo vehiculo = vehiculoService.obtenerVehiculo(placa);
            if (vehiculo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return new ResponseEntity<>(vehiculo, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo vehiculo) {
        if (ObjectUtils.isEmpty(vehiculo) || 
            vehiculo.getCliente() == null || 
            vehiculo.getCliente().getDocumento() == null || 
            vehiculo.getCliente().getDocumento().isBlank() || 
            vehiculo.getTipoVehiculo().isBlank()) {
            return new ResponseEntity<>(vehiculo, HttpStatus.BAD_REQUEST);
        
        }
        try {
            Vehiculo creado = vehiculoService.insertarVehiculo(vehiculo);
            if (creado == null) {
                return new ResponseEntity<>(vehiculo, HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(vehiculo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{placa}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable String placa, @RequestBody Vehiculo vehiculo) {
        if (placa == null || placa.isBlank() || ObjectUtils.isEmpty(vehiculo)) {
            return new ResponseEntity<>(vehiculo, HttpStatus.BAD_REQUEST);
        }
        try {
            Vehiculo actualizado = vehiculoService.actualizarVehiculo(placa, vehiculo);
            if (actualizado == null) {
                return new ResponseEntity<>(vehiculo, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(vehiculo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> eliminar(@PathVariable String placa) {
        if (placa==null || placa.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            boolean eliminado = vehiculoService.eliminarVehiculo(placa);
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
