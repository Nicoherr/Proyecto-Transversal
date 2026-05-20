package com.marketplace.pedido.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class PedidoRequestDTO {
    //Recibe los datos del usuario al crear un Reporte.
    //Usamos lo mismo de la clase Reporte pero sin la notaciones JPA y sin @Entity y @Table.
    @NotNull(message = "Debes indicar el ID del producto")
    private Long productoId; // ID del producto en el microservicio de producto

    @NotBlank(message = "Ingresa el nombre del producto")
    private String nomProducto;

    @NotBlank(message = "Debes especificar el tipo de producto")
    private String tipoProducto;

    @NotNull(message = "Debes ingresar el precio del producto que deseas vender")
    private int precio;
}
