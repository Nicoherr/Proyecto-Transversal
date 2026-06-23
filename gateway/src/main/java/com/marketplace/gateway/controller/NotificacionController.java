package com.marketplace.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Value("${services.notificacion.url:http://localhost:8089}")
    private String notificacionUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(restTemplate.getForObject(notificacionUrl + "/api/v1/notificaciones", Object.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return ResponseEntity.ok(restTemplate.getForObject(notificacionUrl + "/api/v1/notificaciones/" + id, Object.class));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Object body) {
        return ResponseEntity.ok(restTemplate.postForObject(notificacionUrl + "/api/v1/notificaciones", body, Object.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        restTemplate.delete(notificacionUrl + "/api/v1/notificaciones/" + id);
        return ResponseEntity.noContent().build();
    }
}