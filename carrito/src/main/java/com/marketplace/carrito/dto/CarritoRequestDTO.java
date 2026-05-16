package com.marketplace.carrito.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CarritoRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    // @NotNull porque es Long (no String), no existe @NotBlank para números
    @Positive(message = "El ID del usuario debe ser un número positivo")
    // @Positive asegura que no vengan IDs negativos o cero
    private Long usuarioId;
}
