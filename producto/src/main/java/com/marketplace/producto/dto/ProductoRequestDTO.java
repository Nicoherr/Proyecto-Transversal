package com.marketplace.producto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    // @NotBlank verifica que no sea null, vacío ni solo espacios
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    // Opcional — puede venir null
    private String descripcion;

    @Positive(message = "El precio debe ser mayor a 0")
    // @Positive asegura que el precio no sea negativo ni cero
    private double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    // @Min(0) porque un stock de 0 es válido
    private int stock;

    @NotNull(message = "El ID del vendedor es obligatorio")
    // @NotNull porque es Long, no String
    @Positive(message = "El ID del vendedor debe ser un número positivo")
    private Long vendedorId;
}