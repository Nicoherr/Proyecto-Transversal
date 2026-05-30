package com.marketplace.usuario.controller;
import com.marketplace.usuario.assemblers.UsuarioModelAssembler;
import com.marketplace.usuario.dto.UsuarioRequestDTO;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import com.marketplace.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// Esta es la versión 2 del controller — igual al original pero con HATEOAS
// Devuelve EntityModel y CollectionModel en vez de ResponseEntity directo
@Tag(name = "Usuarios V2", description = "Operaciones con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/v2/usuario")
public class UsuarioControllerV2 {

    private final UsuarioService service;
    private final UsuarioModelAssembler assembler;

    public UsuarioControllerV2(UsuarioService service, UsuarioModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // CollectionModel envuelve la lista de usuarios y agrega un link "self" a la colección completa
    @Operation(summary = "Listar todos los usuarios con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<UsuarioResponseDTO>> listar() {
        log.info("GET /api/v2/usuario - Listar usuarios con HATEOAS");

        List<EntityModel<UsuarioResponseDTO>> usuarios = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).listar()).withSelfRel());
    }

    // EntityModel envuelve un solo usuario y agrega los links definidos en el assembler
    @Operation(summary = "Obtener usuario por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<UsuarioResponseDTO> obtener(@PathVariable Long id) {
        log.info("GET /api/v2/usuario/{} - Obtener usuario con HATEOAS", id);
        return assembler.toModel(service.obtener(id));
    }

    @Operation(summary = "Crear usuario con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("POST /api/v2/usuario - Crear usuario con HATEOAS");
        EntityModel<UsuarioResponseDTO> model = assembler.toModel(service.crear(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar usuario con HATEOAS")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<UsuarioResponseDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        log.info("PUT /api/v2/usuario/{} - Actualizar usuario con HATEOAS", id);
        return assembler.toModel(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar usuario con HATEOAS")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v2/usuario/{} - Eliminar usuario con HATEOAS", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
