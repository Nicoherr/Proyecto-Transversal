package com.marketplace.carrito.controller;
import com.marketplace.carrito.dto.*;
import com.marketplace.carrito.service.CarritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CarritoResponseDTO> crear(@RequestBody CarritoRequestDTO dto) {
        return new ResponseEntity<>(service.crearCarrito(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerCarrito(id));
    }

    @PostMapping("/productos")
    public ResponseEntity<CarritoProductoResponseDTO> agregarProducto(@RequestBody CarritoProductoRequestDTO dto) {
        return new ResponseEntity<>(service.agregarProducto(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{carritoId}/productos")
    public ResponseEntity<List<CarritoProductoResponseDTO>> listarProductos(@PathVariable Long carritoId) {
        return ResponseEntity.ok(service.listarProductos(carritoId));
    }
}

