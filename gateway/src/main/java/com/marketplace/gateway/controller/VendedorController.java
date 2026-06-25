package com.marketplace.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

	@Value("${services.vendedor.url:http://localhost:8083}")
	private String vendedorUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(restTemplate.getForObject(vendedorUrl + "/api/vendedores", Object.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable long id) {
		return ResponseEntity.ok(restTemplate.getForObject(vendedorUrl + "/api/vendedores/" + id, Object.class));
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Object body) {
		return ResponseEntity.ok(restTemplate.postForObject(vendedorUrl + "/api/vendedores", body, Object.class));
	}
}
