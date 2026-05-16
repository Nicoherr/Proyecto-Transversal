package com.marketplace.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioRequestDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    // @NotNull porque es Long, no String
    @Min(value = 1, message = "El ID del producto debe ser mayor a 0")
    // @Min asegura que no vengan IDs negativos o cero
    private Long productoId;

    @Min(value = 0, message = "El stock no puede ser negativo")
    // @Min(0) porque un stock de 0 es válido (producto sin stock)
    private int stock;

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    // Es Integer (no int) así que puede ser null — si no viene, el modelo usa 5 por defecto
    private Integer stockMinimo;
}