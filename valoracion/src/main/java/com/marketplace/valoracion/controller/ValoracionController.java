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

    //CREAR
    @PostMapping
    public ResponseEntity<ValoracionResponseDTO> postValoracion(@Valid @RequestBody ValoracionRequestDTO newValoracion) {
        ValoracionResponseDTO valoracion = valoracionService.makeValoracion(newValoracion);
        return ResponseEntity.status(HttpStatus.CREATED).body(valoracion);
    }
c
    //LISTAR
    @GetMapping
    public ResponseEntity<List<ValoracionResponseDTO>> getValoraciones(){
        return ResponseEntity.ok(valoracionService.findAllValoraciones());
    }

    //BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<ValoracionRequestDTO> getValoracion(@PathVariable long id){
        return ResponseEntity.ok(valoracionService.findValoracionById(id));
    }

    //ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<ValoracionResponseDTO> putValoracion(@PathVariable long id, @RequestBody ValoracionRequestDTO user){
        return ResponseEntity.ok(valoracionService.updateValoracion(id,user));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<ValoracionRequestDTO> deleteValoracion(@PathVariable long id){
        valoracionService.deleteValoracion(id);
        return ResponseEntity.noContent().build();
    }
}
