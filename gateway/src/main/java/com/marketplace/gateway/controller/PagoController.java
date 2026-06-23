package com.marketplace.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Value("${services.pago.url:http://localhost:8087}")
    private String pagoUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(restTemplate.getForObject(pagoUrl + "/api/v1/pagos", Object.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return ResponseEntity.ok(restTemplate.getForObject(pagoUrl + "/api/v1/pagos/" + id, Object.class));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Object body) {
        return ResponseEntity.ok(restTemplate.postForObject(pagoUrl + "/api/v1/pagos", body, Object.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        restTemplate.delete(pagoUrl + "/api/v1/pagos/" + id);
        return ResponseEntity.noContent().build();
    }
}
