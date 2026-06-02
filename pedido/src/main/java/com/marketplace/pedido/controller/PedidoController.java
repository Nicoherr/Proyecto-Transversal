package com.marketplace.pedido.controller;

import com.marketplace.pedido.DTO.ExceptionDTO;
import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operaciones relacionadas con la gestión de pedidos del marketplace")
public class PedidoController {
    private final PedidoService pedidoService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener todos los pedidos",
            description = "Retorna una lista completa de todos los pedidos registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos obtenida exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getPedidos() {
        return ResponseEntity.ok(pedidoService.findAllPedidos());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener pedido por ID",
            description = "Retorna un pedido específico buscando por su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido encontrado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PedidoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploPedido",
                                    value = "{\"id\": 1, \"nomProducto\": \"Audífonos Bluetooth\", \"tipoProducto\": \"Electrónica\", \"precio\": 25990}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getPedido(
            @Parameter(description = "ID único del pedido", required = true, example = "1")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(pedidoService.findPedidoById(id));
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Crear un nuevo pedido",
            description = "Registra un nuevo pedido en el sistema asociado a un producto existente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para crear el pedido",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PedidoRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploRequest",
                                    value = "{\"productoId\": 3, \"nomProducto\": \"Audífonos Bluetooth\", \"tipoProducto\": \"Electrónica\", \"precio\": 25990}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido creado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> postPedido(
            @Valid @RequestBody PedidoRequestDTO newPedido
    ) {
        PedidoResponseDTO pedido = pedidoService.makePedido(newPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Operation(
            summary = "Eliminar un pedido",
            description = "Elimina un pedido del sistema por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pedido eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(
            @Parameter(description = "ID único del pedido a eliminar", required = true, example = "1")
            @PathVariable long id
    ) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
