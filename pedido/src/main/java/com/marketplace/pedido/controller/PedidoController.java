package com.marketplace.pedido.controller;

import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    //Create
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> postPedido(@Valid @RequestBody PedidoRequestDTO newPedido){
        PedidoResponseDTO pedido = pedidoService.makePedido(newPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
    //Listar
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getPedido(){
        return ResponseEntity.ok(pedidoService.findAllPedidos());
    }

    //Buscar
    @GetMapping("{id}")
    public ResponseEntity<PedidoRequestDTO> getPedido(@PathVariable long id){
        return ResponseEntity.ok(pedidoService.findPedidoById(id));
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<PedidoRequestDTO> deletePedido(@PathVariable long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }


}
