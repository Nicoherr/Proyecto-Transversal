package com.marketplace.gateway.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

	@Value("${services.carrito.url:http://localhost:8081}")
	private String carritoUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(restTemplate.getForObject(carritoUrl + "/api/carritos", Object.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable long id) {
		return ResponseEntity.ok(restTemplate.getForObject(carritoUrl + "/api/carritos/" + id, Object.class));
	}

	@GetMapping("/{carritoId}/productos")
	public ResponseEntity<Object> listarProductos(@PathVariable long carritoId) {
		return ResponseEntity.ok(restTemplate.getForObject(carritoUrl + "/api/carritos/" + carritoId + "/productos", Object.class));
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Object body) {
		return ResponseEntity.ok(restTemplate.postForObject(carritoUrl + "/api/carritos", body, Object.class));
	}

	@PostMapping("/productos")
	public ResponseEntity<Object> agregarProducto(@RequestBody Object body) {
		return ResponseEntity.ok(restTemplate.postForObject(carritoUrl + "/api/carritos/productos", body, Object.class));
	}
}
