package com.marketplace.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

	@Value("${services.producto.url:http://localhost:8084}")
	private String productoUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(restTemplate.getForObject(productoUrl + "/api/producto", Object.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable long id) {
		return ResponseEntity.ok(restTemplate.getForObject(productoUrl + "/api/producto/" + id, Object.class));
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Object body) {
		return ResponseEntity.ok(restTemplate.postForObject(productoUrl + "/api/producto", body, Object.class));
	}
}
