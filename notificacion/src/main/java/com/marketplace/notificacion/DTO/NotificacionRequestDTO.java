package com.marketplace.notificacion.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class NotificacionRequestDTO {
    //Devuelve datos al usuario como respuesta
    //Usamos los campos de la clase reporte pero solo los que deve llenar un usuario por la Api

    @NotBlank(message = "El asunto deve estar descrito")
    private String asunto;
    @NotBlank(message = "El mensaje no puede estar vacio")
    private String mensaje;
}
