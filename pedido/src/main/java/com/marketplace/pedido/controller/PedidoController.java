package com.marketplace.pedido.controller;

import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getPedidos() {
        return ResponseEntity.ok(pedidoService.findAllPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getPedido(@PathVariable long id) {
        return ResponseEntity.ok(pedidoService.findPedidoById(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> postPedido(@Valid @RequestBody PedidoRequestDTO newPedido) {
        PedidoResponseDTO pedido = pedidoService.makePedido(newPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
