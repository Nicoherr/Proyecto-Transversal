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

    //Create
    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@Valid @RequestBody PedidoNewDTO pedido){
        return ResponseEntity.ok(pedidoService.createPedido(pedido));
    }
    //Listar
    @GetMapping
    public ResponseEntity<List<Pedido>> listar(){
        List<Pedido> pedido = pedidoService.findAll();
        if (pedido.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedido);
    }

    //Buscar
    @GetMapping("{id}")
    public ResponseEntity<Pedido> buscar(@PathVariablec Long id){
        try{
            Pedido pedido =pedidoService.findById(id):
            return ResponseEntity.ok(pedido);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar
    @DeleteMapping("{id}")
    public void eliminarPedido(Long id)


}
