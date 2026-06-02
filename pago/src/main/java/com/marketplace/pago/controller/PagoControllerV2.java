package com.marketplace.pago.controller;

import com.marketplace.pago.assemblers.PagoModelAssembler;
import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.service.PagoService;
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

@Tag(name = "Pagos V2", description = "Operaciones de pago con HATEOAS")
@Slf4j
@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    private final PagoService service;
    private final PagoModelAssembler assembler;

    public PagoControllerV2(PagoService service, PagoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los pagos con HATEOAS")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<PagoResponseDTO>> listar() {
        log.info("GET /api/v2/pagos - Listar con HATEOAS");
        List<EntityModel<PagoResponseDTO>> pagos = service.findAllPagos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener pago por ID con HATEOAS")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<PagoResponseDTO> obtener(
            @Parameter(description = "ID del pago", required = true, example = "1")
            @PathVariable long id) {
        log.info("GET /api/v2/pagos/{} - Obtener con HATEOAS", id);
        return assembler.toModel(service.findPagosById(id));
    }

    @Operation(summary = "Procesar pago con HATEOAS")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoResponseDTO>> crear(@Valid @RequestBody PagoRequestDTO dto) {
        log.info("POST /api/v2/pagos - Crear con HATEOAS");
        EntityModel<PagoResponseDTO> model = assembler.toModel(service.makePago(dto));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar pago")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pago", required = true, example = "1")
            @PathVariable long id) {
        log.info("DELETE /api/v2/pagos/{} - Eliminar", id);
        service.deletePago(id);
        return ResponseEntity.noContent().build();
    }
}
