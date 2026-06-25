package com.marketplace.gateway.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Value("${services.usuario.url:http://localhost:8080}")
	private String usuarioUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(restTemplate.getForObject(usuarioUrl + "/api/v1/api/usuario", Object.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable long id) {
		return ResponseEntity.ok(restTemplate.getForObject(usuarioUrl + "/api/v1/api/usuario/" + id, Object.class));
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Object body) {
		return ResponseEntity.ok(restTemplate.postForObject(usuarioUrl + "/api/v1/api/usuario", body, Object.class));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Object body) {
		restTemplate.put(usuarioUrl + "/api/v1/api/usuario/" + id, body);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		restTemplate.delete(usuarioUrl + "/api/v1/api/usuario/" + id);
		return ResponseEntity.noContent().build();
	}
}
