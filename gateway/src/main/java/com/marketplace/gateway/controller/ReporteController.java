package com.marketplace.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Value("${services.reporte.url:http://localhost:8090}")
    private String reporteUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(restTemplate.getForObject(reporteUrl + "/api/v1/reportes", Object.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return ResponseEntity.ok(restTemplate.getForObject(reporteUrl + "/api/v1/reportes/" + id, Object.class));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Object body) {
        return ResponseEntity.ok(restTemplate.postForObject(reporteUrl + "/api/v1/reportes", body, Object.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        restTemplate.delete(reporteUrl + "/api/v1/reportes/" + id);
        return ResponseEntity.noContent().build();
    }
}
