package com.marketplace.reporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "reporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Ingresa un texto valido")
    @Column(nullable = false)
    private String tipo;

    @NotBlank(message = "Ingresa un texto valido")
    @Column(length = 512)
    private String descripcion;

    @Column()
    private Date fecha;

    @NotBlank(message = "Indicar el estado del reporte")
    @Column(nullable = false)
    private Boolean estado;

}
