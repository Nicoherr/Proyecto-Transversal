package com.marketplace.vendedor.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendedorRequestDTO {

    @NotBlank(message = "El nombre de la tienda no puede estar vacío")
    // @NotBlank verifica que no sea null, vacío ni solo espacios
    @Size(min = 2, max = 100, message = "El nombre de la tienda debe tener entre 2 y 100 caracteres")
    private String nombreTienda;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    // No es @NotBlank porque la descripción es opcional — puede venir null
    private String descripcion;

    @NotNull(message = "El ID del usuario es obligatorio")
    // @NotNull porque es Long, no String
    @Positive(message = "El ID del usuario debe ser un número positivo")
    // @Positive asegura que no vengan IDs negativos o cero
    private Long usuarioId;
}