package com.marketplace.pago.controller;

import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.model.Pago;
import com.marketplace.pago.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> getPagos() {
        return ResponseEntity.ok(pagoService.findAllPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> getPago(@PathVariable long id) {
        return ResponseEntity.ok(pagoService.findPagosById(id));
    }

    @PostMapping
    public ResponseEntity<PagoResponseDTO> postPago(@Valid @RequestBody PagoRequestDTO pagoDTO) {
        PagoResponseDTO nuevo = pagoService.makePago(pagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }


}
