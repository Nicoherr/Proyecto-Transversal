package com.marketplace.valoracion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "valoracion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "El id del producto es obligatorio")
    @Column(nullable = false)
    private Long productoId;

    @NotNull(message = "El número de estrellas es obligatorio")
    @Column(nullable = false)
    private int numEstrella;

    @NotBlank(message = "Ingresa una recomendacion del producto")
    @Column(nullable = false, length = 512)
    private String recomendacion;

    @NotBlank(message = "Añade una sugerencia")
    @Column(nullable = false, length = 500)
    private String sugerencia;
}

