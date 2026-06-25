package com.marketplace.gateway.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

	@Value("${services.inventario.url:http://localhost:8082}")
	private String inventarioUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(restTemplate.getForObject(inventarioUrl + "/api/v1/api/inventarios", Object.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable long id) {
		return ResponseEntity.ok(restTemplate.getForObject(inventarioUrl + "/api/v1/api/inventarios/" + id, Object.class));
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Object body) {
		return ResponseEntity.ok(restTemplate.postForObject(inventarioUrl + "/api/v1/api/inventarios", body, Object.class));
	}
}
