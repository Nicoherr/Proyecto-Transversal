package com.marketplace.carrito.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito_producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarritoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // @ManyToOne apunta a la entidad Carrito, no a un Long
    // Muchos CarritoProducto pertenecen a un solo Carrito
    // @JoinColumn le dice a JPA que la columna carrito_id es la llave foránea
    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    // Producto es otro microservicio, así que solo guardamos su ID
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    // Cantidad de este producto en el carrito
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
}
