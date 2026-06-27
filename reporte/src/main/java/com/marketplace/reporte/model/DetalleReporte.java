package com.marketplace.reporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_reporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La observación no puede estar vacía")
    @Column(nullable = false, length = 512)
    private String observacion;

    @Column(nullable = false)
    private int valor;

    // Relación ManyToOne: muchos detalles pertenecen a un solo reporte
    @ManyToOne
    @JoinColumn(name = "reporte_id", nullable = false)
    private Reporte reporte;
}
