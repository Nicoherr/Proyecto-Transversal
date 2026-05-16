package com.marketplace.usuario.controller;
import com.marketplace.usuario.dto.UsuarioRequestDTO;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import com.marketplace.usuario.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@RequestBody UsuarioRequestDTO dto) {
        log.info("POST /api/usuario - Solicitud para crear usuario con email: {}", dto.getEmail());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        log.info("GET /api/usuario - Solicitud para listar todos los usuarios");
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/usuario/{} - Solicitud para obtener usuario", id);
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        log.info("PUT /api/usuario/{} - Solicitud para actualizar usuario", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/usuario/{} - Solicitud para eliminar usuario", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}