package com.marketplace.producto.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "vendedor_id", nullable = false)
    private Long vendedorId;

    @Column(name = "activo")
    private boolean activo = true;
}
