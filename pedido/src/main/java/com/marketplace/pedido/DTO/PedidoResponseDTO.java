package com.marketplace.pedido.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
        //Devuelve datos al usuario como respuesta
        //Usamos los campos de la clase reporte pero solo los que deve llenar un usuario por la Api

        private long id = 0;

        @NotBlank(message = "Ingresa el nombre del producto")
        private String nomProducto;

        @NotBlank(message = "Deves especificar el tipo de producto")
        private String tipoProducto;

        @NotNull(message = "Deves ingresar el precio del producto que deseas vender")
        private int precio;



}
