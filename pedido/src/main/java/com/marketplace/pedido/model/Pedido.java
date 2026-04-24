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

    @NotBlank(message = "Ingresa el nombre del producto")
    @Column(nullable = false)
    private String nomProducto;

    @NotBlank(message = "Deves especificar el tipo de producto")
    @Column(nullable = false)
    private String tipoProducto;

    @NotNull(message = "El precio es obligatorio")
    @Column(nullable = false)
    private int precio;
}
