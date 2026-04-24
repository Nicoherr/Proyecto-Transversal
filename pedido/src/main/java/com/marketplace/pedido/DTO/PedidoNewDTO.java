package com.marketplace.pedido.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoNewDTO {
        @NotBlank(message = "Ingresa el nombre del producto")
        private String nomProducto;
        @NotBlank(message = "Deves especificar el tipo de producto")
        private String tipoProducto;
}
