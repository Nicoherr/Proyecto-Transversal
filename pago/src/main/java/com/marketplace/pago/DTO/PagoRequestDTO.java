package com.marketplace.pago.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequestDTO {

    @NotNull(message = "Debes indicar el ID del pedido a pagar")
    private Long pedidoId; // ID del pedido en el microservicio de pedido

    @NotBlank(message = "Escribe el metodo de pago")
    private String metodoPago;
}

