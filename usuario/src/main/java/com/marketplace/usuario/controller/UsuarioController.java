package com.marketplace.usuario.controller;
import com.marketplace.usuario.dto.UsuarioRequestDTO;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import com.marketplace.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Tag agrupa todos los endpoints de este controller bajo el nombre "Usuarios" en Swagger
// Así en la UI aparece una sección llamada "Usuarios" con todos sus métodos adentro
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
@Slf4j
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // @Operation describe qué hace este endpoint específico en Swagger
    // summary: el título corto que aparece al lado del método POST en la UI
    // description: el texto explicativo más largo cuando abres el endpoint
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    // @ApiResponses agrupa múltiples @ApiResponse para documentar los posibles códigos HTTP
    // que puede retornar este endpoint — así el que usa la API sabe qué esperar
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("POST /api/usuario - Solicitud para crear usuario con email: {}", dto.getEmail());
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios")
    // Cuando solo hay una respuesta posible se puede usar @ApiResponse directo sin @ApiResponses
    @ApiResponse(responseCode = "200", description = "Lista retornada con éxito")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        log.info("GET /api/usuario - Solicitud para listar todos los usuarios");
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            // 400 porque nuestro GlobalExceptionHandler retorna BAD_REQUEST cuando no encuentra el usuario
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/usuario/{} - Solicitud para obtener usuario", id);
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        log.info("PUT /api/usuario/{} - Solicitud para actualizar usuario", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            // 204 significa "éxito pero sin contenido" — típico del DELETE
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/usuario/{} - Solicitud para eliminar usuario", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}