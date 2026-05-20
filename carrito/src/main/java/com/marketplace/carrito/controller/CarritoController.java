package com.marketplace.carrito.controller;

import com.marketplace.carrito.dto.*;
import com.marketplace.carrito.service.CarritoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Habilita los logs
@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CarritoResponseDTO> crear(@Valid @RequestBody CarritoRequestDTO dto) {
        log.info("POST /api/carritos - Solicitud para crear carrito para usuario ID: {}", dto.getUsuarioId());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/carritos/{} - Solicitud para obtener carrito", id);
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping("/productos")
    public ResponseEntity<CarritoProductoResponseDTO> agregarProducto(@RequestBody CarritoProductoRequestDTO dto) {
        log.info("POST /api/carritos/productos - Agregar producto ID: {} al carrito ID: {}", dto.getProductoId(), dto.getCarritoId());
        return new ResponseEntity<>(service.agregarProducto(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{carritoId}/productos")
    public ResponseEntity<List<CarritoProductoResponseDTO>> listarProductos(@PathVariable Long carritoId) {
        log.info("GET /api/carritos/{}/productos - Listar productos del carrito", carritoId);
        return ResponseEntity.ok(service.listarProductos(carritoId));
    }
}