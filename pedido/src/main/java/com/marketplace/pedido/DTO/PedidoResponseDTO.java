package com.marketplace.pedido.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
        private long id = 0;
        private Long productoId;
        private String nomProducto;
        private String tipoProducto;
        private int precio;
        private String direccionEntrega;
}

