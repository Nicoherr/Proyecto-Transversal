package com.marketplace.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // ID del producto pedido (referencia al microservicio de Producto)
    @NotNull(message = "El id del producto es obligatorio")
    @Column(nullable = false)
    private Long productoId;

    @NotBlank(message = "Ingresa el nombre del producto")
    @Column(nullable = false, length = 200)
    private String nomProducto;

    @NotBlank(message = "Debes especificar el tipo de producto")
    @Column(nullable = false, length = 30)
    private String tipoProducto;

    @NotNull(message = "Debes ingresar el precio del producto")
    @Column(nullable = false)
    private int precio;

    @NotBlank(message = "Ingresa la dirección para el despacho")
    @Column
    private String direccionEntrega;
}
