package com.marketplace.carrito.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Solo guardamos el ID del usuario porque es otro microservicio
    // No podemos hacer @ManyToOne real entre microservicios distintos
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
}