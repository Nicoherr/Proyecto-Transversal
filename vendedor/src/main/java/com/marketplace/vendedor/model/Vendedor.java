package com.marketplace.vendedor.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vendedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_tienda", nullable = false, length = 100)
    private String nombreTienda;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "reputacion")
    private double reputacion = 0.0;

    @Column(name = "cantidad_valoraciones")
    private int cantidadValoraciones = 0;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "activo")
    private boolean activo = true;
}

