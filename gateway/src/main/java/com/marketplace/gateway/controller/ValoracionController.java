package com.marketplace.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/valoracion")
public class ValoracionController {

    @Value("${services.valoracion.url:http://localhost:8088}")
    private String valoracionUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(restTemplate.getForObject(valoracionUrl + "/api/v1/valoracion", Object.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return ResponseEntity.ok(restTemplate.getForObject(valoracionUrl + "/api/v1/valoracion/" + id, Object.class));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Object body) {
        return ResponseEntity.ok(restTemplate.postForObject(valoracionUrl + "/api/v1/valoracion", body, Object.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        restTemplate.delete(valoracionUrl + "/api/v1/valoracion/" + id);
        return ResponseEntity.noContent().build();
    }
}