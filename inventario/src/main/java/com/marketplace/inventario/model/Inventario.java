package com.marketplace.inventario.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "inventario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "stock_minimo")
    private int stockMinimo = 5;
}

