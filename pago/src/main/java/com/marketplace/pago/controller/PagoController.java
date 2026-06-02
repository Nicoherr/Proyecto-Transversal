package com.marketplace.pago.controller;

import com.marketplace.pago.DTO.ExceptionDTO;
import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.model.Pago;
import com.marketplace.pago.service.PagoService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Operaciones relacionadas con el procesamiento de pagos")
public class PagoController {
    private final PagoService pagoService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener todos los pagos",
            description = "Retorna una lista completa de todos los pagos registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pagos obtenida exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PagoResponseDTO.class))
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
    public ResponseEntity<List<PagoResponseDTO>> getPagos() {
        return ResponseEntity.ok(pagoService.findAllPagos());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @Operation(
            summary = "Obtener pago por ID",
            description = "Retorna un pago específico buscando por su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago encontrado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PagoResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploPago",
                                    value = "{\"id\": 1, \"metodoPago\": \"Tarjeta de crédito\", \"comprobante\": \"COMP-20260601-001\", \"fecha\": \"2026-06-01T12:00:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pago no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> getPago(
            @Parameter(description = "ID único del pago", required = true, example = "1")
            @PathVariable long id
    ) {
        return ResponseEntity.ok(pagoService.findPagosById(id));
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @Operation(
            summary = "Procesar un nuevo pago",
            description = "Registra y procesa el pago de un pedido existente en el sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para procesar el pago",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PagoRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "EjemploRequest",
                                    value = "{\"pedidoId\": 1, \"metodoPago\": \"Tarjeta de crédito\"}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pago procesado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PagoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos o pedido no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<PagoResponseDTO> postPago(
            @Valid @RequestBody PagoRequestDTO pagoDTO
    ) {
        PagoResponseDTO nuevo = pagoService.makePago(pagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Operation(
            summary = "Eliminar un pago",
            description = "Elimina el registro de un pago del sistema por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pago eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pago no encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionDTO.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(
            @Parameter(description = "ID único del pago a eliminar", required = true, example = "1")
            @PathVariable long id
    ) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }
}
