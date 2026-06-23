package com.marketplace.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Value("${services.pedido.url:http://localhost:8086}")
    private String pedidoUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(restTemplate.getForObject(pedidoUrl + "/api/v1/pedidos", Object.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return ResponseEntity.ok(restTemplate.getForObject(pedidoUrl + "/api/v1/pedidos/" + id, Object.class));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Object body) {
        return ResponseEntity.ok(restTemplate.postForObject(pedidoUrl + "/api/v1/pedidos", body, Object.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        restTemplate.delete(pedidoUrl + "/api/v1/pedidos/" + id);
        return ResponseEntity.noContent().build();
    }
}