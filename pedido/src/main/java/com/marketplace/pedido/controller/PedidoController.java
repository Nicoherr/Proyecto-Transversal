package com.marketplace.pedido.controller;

import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.assemblers.PedidoModelAssembler;
import com.marketplace.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Pedidos", description = "Operaciones relacionadas con la gestión de pedidos del marketplace")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @Operation(summary = "Obtener todos los pedidos", description = "Retorna lista de pedidos con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PedidoResponseDTO>> listar() {
        List<EntityModel<PedidoResponseDTO>> pedidos = pedidoService.findAllPedidos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener pedido por ID", description = "Retorna un pedido con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public PedidoResponseDTO obtener(
            @Parameter(description = "ID del pedido", required = true, example = "1")
            @PathVariable long id) {
        return assembler.toModel(pedidoService.findPedidoById(id)).getContent();
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Registra un nuevo pedido asociado a un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PedidoResponseDTO>> crear(
            @Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO nuevo = pedidoService.makePedido(dto);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoController.class).obtener(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido del sistema por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del pedido a eliminar", required = true, example = "1")
            @PathVariable long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}