package com.marketplace.pedido.DTO;
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class PedidoDTO {
    //Devuelve datos al usuario como respuesta
    //Usamos lo mismo de la clase Reporte pero sin la notaciones JPA y sin @Entity y @Table.
    private Long id = 0;

    @NotBlank(message = "Ingresa el nombre del producto")
    private String nomProducto;

    @NotBlank(message = "Deves especificar el tipo de producto")
    private String tipoProducto;

    @NotNull(message = "Deves ingresar el precio del producto que deseas vender")
    private int precio;
}
