package com.marketplace.pago.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO que representa la respuesta del microservicio de Pedido
// Solo los campos que necesitamos validar antes de crear un pago
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoClientDTO {
    private Long id;
    private String nomProducto;
    private String tipoProducto;
    private int precio;
}