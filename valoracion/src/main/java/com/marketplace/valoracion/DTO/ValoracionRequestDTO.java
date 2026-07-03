package com.marketplace.valoracion.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionRequestDTO {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productoId;

    @Min(value = 1, message = "El mínimo de estrellas es 1")
    @Max(value = 5, message = "El máximo de estrellas es 5")
    private int numEstrella;

    @NotBlank(message = "Ingresa una recomendacion del producto")
    @Size(min = 10, message = "La recomendación debe tener al menos 10 caracteres")
    private String recomendacion;

    @NotBlank(message = "Añade una sugerencia para el vendedor")
    @Size(min = 10, message = "La sugerencia debe tener al menos 10 caracteres")
    private String sugerencia;
}
