package com.marketplace.valoracion.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class ValoracionRequestDTO {
    //Recibe los datos del usuario al crear un Reporte.
    //Usamos los campos de la clase reporte pero solo los que deve llenar un usuario por la Api
    @NotNull(message = "El campo no puede ser nulo")
    @Min(value = 1, message = "Minimo 1 estrella")
    @Max(value = 5, message = "maximo 5 estrellas")
    private int numEstrella;

    @NotBlank(message = "Ingresa una recomendacion del producto")
    private String recomendacion;
}
