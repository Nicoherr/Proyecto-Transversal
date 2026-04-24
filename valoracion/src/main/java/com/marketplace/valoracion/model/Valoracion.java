package com.marketplace.valoracion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column()
    private int numEstrella;

    @NotBlank(message = "Ingresa una recomendacion del producto")
    @Column(length = 512)
    private String recomendacion;
}
