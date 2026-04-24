package com.marketplace.pedido.controller;

import com.marketplace.pedido.DTO.PedidoDTO;
import com.marketplace.pedido.DTO.PedidoNewDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@Valid @RequestBody PedidoNewDTO pedido){
        return ResponseEntity.ok(pedidoService.createPedido(pedido));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getPedido(){
        return ResponseEntity.ok(pedidoService.getPedidos());
    }
}
