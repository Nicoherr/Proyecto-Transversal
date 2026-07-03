package com.marketplace.pedido.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class PedidoRequestDTO {

    @NotNull(message = "Debes indicar el ID del producto")
    private Long productoId; // ID del producto en el microservicio de producto

    @NotBlank(message = "Ingresa el nombre del producto")
    @Size(min=3, message = "El nombre del producto deve tener el amenos 3 caracteres")
    private String nomProducto;

    @NotBlank(message = "Debes especificar el tipo de producto")
    private String tipoProducto;

    @NotNull(message = "Debes ingresar el precio del producto que deseas vender")
    @Positive(message = "El precio deve ser un numero positivo")
    private int precio;

    @NotBlank(message = "Ingresa la direccion para el despacho")
    private String direccionEntrega;
}
