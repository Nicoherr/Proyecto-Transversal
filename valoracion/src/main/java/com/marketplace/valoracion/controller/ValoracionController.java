package com.marketplace.valoracion.controller;

import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.service.ValoracionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("valoracion")
@RequiredArgsConstructor
public class ValoracionController {

    @Autowired
    private final ValoracionService valoracionService;

    @GetMapping
    public ResponseEntity<List<ValoracionResponseDTO>> getValoraciones() {
        return ResponseEntity.ok(valoracionService.findAllValoraciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> getValoracion(@PathVariable long id) {
        return ResponseEntity.ok(valoracionService.findValoracionById(id));
    }

    @PostMapping
    public ResponseEntity<ValoracionResponseDTO> postValoracion(@Valid @RequestBody ValoracionRequestDTO newValoracion) {
        ValoracionResponseDTO valoracion = valoracionService.makeValoracion(newValoracion);
        return ResponseEntity.status(HttpStatus.CREATED).body(valoracion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> putValoracion(@PathVariable long id, @Valid @RequestBody ValoracionRequestDTO dto) {
        return ResponseEntity.ok(valoracionService.updateValoracion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValoracion(@PathVariable long id) {
        valoracionService.deleteValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
