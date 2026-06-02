package com.marketplace.notificacion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoClientDTO {
    private Long id;
    private String nomProducto;
    private String tipoProducto;
    private int precio;
}