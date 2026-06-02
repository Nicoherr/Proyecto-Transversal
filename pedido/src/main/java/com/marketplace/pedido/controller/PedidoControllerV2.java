package com.marketplace.pedido.controller;

import com.marketplace.pedido.assemblers.PedidoModelAssembler;
import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Pedidos V2", description = "Operaciones de pedido con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoControllerV2 {

    private final PedidoService service;
    private final PedidoModelAssembler assembler;

    public PedidoControllerV2(PedidoService service, PedidoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los pedidos con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<PedidoResponseDTO>> listar() {
        log.info("GET /api/v2/pedidos - Listar con HATEOAS");
        List<EntityModel<PedidoResponseDTO>> pedidos = service.findAllPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener pedido por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<PedidoResponseDTO> obtener(
            @Parameter(description = "ID del pedido", required = true, example = "1")
            @PathVariable long id) {
        log.info("GET /api/v2/pedidos/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.findPedidoById(id));
    }

    @Operation(summary = "Crear pedido con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PedidoResponseDTO>> crear(@Valid @RequestBody PedidoRequestDTO dto) {
        log.info("POST /api/v2/pedidos - Crear con HATEOAS");
        EntityModel<PedidoResponseDTO> model = assembler.toModel(service.makePedido(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pedido", required = true, example = "1")
            @PathVariable long id) {
        log.info("DELETE /api/v2/pedidos/{} - Eliminar", id);
        service.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}

