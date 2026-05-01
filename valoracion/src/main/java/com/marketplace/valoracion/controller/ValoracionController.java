package com.marketplace.valoracion.controller;

import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.service.ValoracionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/valoracion")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    //CREATE
    @PostMapping
    public ResponseEntity<ValoracionResponseDTO> guardar(@Valid @RequestBody ValoracionRequestDTO valoracionDTO){
        ValoracionResponseDTO nuevo = valoracionService.createValoracion(valoracionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    //READ
    //BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<ValoracionRequestDTO> buscar(@PathVariable Long id){
        try{
            ValoracionRequestDTO valoracion = valoracionService.findValoracionesById(id);
            return ResponseEntity.ok(valoracion);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
    //LISTAR
    @GetMapping
    public ResponseEntity<List<ValoracionResponseDTO>> listar() {
        List<ValoracionResponseDTO> valoraciones = valoracionService.findAllValoraciones();
        if (valoraciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(valoraciones);
    }
    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        valoracionService.deleteValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
