package com.marketplace.valoracion.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionRequestDTO {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El campo no puede ser nulo")
    private int numEstrella;

    @NotBlank(message = "Ingresa una recomendacion del producto")
    private String recomendacion;
}
