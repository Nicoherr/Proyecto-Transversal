package com.marketplace.vendedor.controller;

import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.service.VendedorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Habilita los logs
@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    private final VendedorService service;

    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VendedorResponseDTO> crear(@RequestBody @Valid VendedorRequestDTO dto) {
        // @Valid activa las validaciones del DTO antes de entrar al service
        log.info("POST /api/vendedores - Solicitud para crear vendedor: {}", dto.getNombreTienda());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VendedorResponseDTO>> listar() {
        log.info("GET /api/vendedores - Solicitud para listar todos los vendedores");
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/vendedores/{} - Solicitud para obtener vendedor", id);
        return ResponseEntity.ok(service.obtener(id));
    }
}
