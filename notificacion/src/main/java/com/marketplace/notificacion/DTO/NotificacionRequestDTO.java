package com.marketplace.notificacion.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionRequestDTO {

    @NotNull(message = "El id del pedido es obligatorio")
    private Long pedidoId;

    @NotBlank(message = "El asunto debe estar descrito")
    private String asunto;

    @NotBlank(message = "El mensaje no puede estar vacio")
    private String mensaje;
}
