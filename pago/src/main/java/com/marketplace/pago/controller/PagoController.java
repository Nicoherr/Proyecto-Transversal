package com.marketplace.pago.controller;

import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.assemblers.PagoModelAssembler;
import com.marketplace.pago.service.PagoService;
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

@Tag(name = "Pagos", description = "Operaciones relacionadas con el procesamiento de pagos")
@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;

    @Operation(summary = "Obtener todos los pagos", description = "Retorna lista de pagos con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PagoResponseDTO>> listar() {
        List<EntityModel<PagoResponseDTO>> pagos = pagoService.findAllPagos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener pago por ID", description = "Retorna un pago con links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public PagoResponseDTO obtener(
            @Parameter(description = "ID del pago", required = true, example = "1")
            @PathVariable long id) {
        return assembler.toModel(pagoService.findPagosById(id)).getContent();
    }

    @Operation(summary = "Procesar un nuevo pago", description = "Registra y procesa el pago de un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago procesado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o pedido no encontrado")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoResponseDTO>> crear(
            @Valid @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO nuevo = pagoService.makePago(dto);
        return ResponseEntity
                .created(linkTo(methodOn(PagoController.class).obtener(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Eliminar un pago", description = "Elimina el registro de un pago por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del pago a eliminar", required = true, example = "1")
            @PathVariable long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }
}