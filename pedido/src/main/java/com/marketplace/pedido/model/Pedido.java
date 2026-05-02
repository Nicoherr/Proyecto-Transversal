package com.marketplace.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //JPA crea la tabla en la BD automaticamente.
@Table(name = "pedido") //Nombre de la Tabla en la Base de datos
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class Pedido {
    @Id// este campo es el ID único de cada registro
    @GeneratedValue(strategy = GenerationType.IDENTITY)// el ID se genera solo, no lo ingresa el usuario
    private long id;

    @NotBlank(message = "Ingresa el nombre del producto")
    @Column(nullable = false, length = 200)//Validamos que el dato no sea nulo en la Base de Datos.
    private String nomProducto;

    @NotBlank(message = "Deves especificar el tipo de producto")
    @Column(nullable = false, length = 30)//Validamos que el dato no sea nulo en la Base de Datos.
    private String tipoProducto;

    @NotNull(message = "Deves ingresar el precio del producto que deseas vender")
    @Column(nullable = false)//Validamos que el dato no sea nulo en la Base de Datos.
    private int precio;
}
