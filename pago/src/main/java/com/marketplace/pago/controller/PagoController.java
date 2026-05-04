package com.marketplace.pago.controller;

import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.model.Pago;
import com.marketplace.pago.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    //CREATE
    @PostMapping
    public ResponseEntity<PagoResponseDTO> postPago(@Valid @RequestBody PagoRequestDTO pagoDTO){
        PagoResponseDTO nuevo = pagoService.makePago(pagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    //BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<PagoRequestDTO> buscar(@PathVariable long id){
        return ResponseEntity.ok(pagoService.findPagosById(id));

    }
    //LISTAR
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> getPagos(){
        return ResponseEntity.ok(pagoService.findAllPagos());
    }

}
