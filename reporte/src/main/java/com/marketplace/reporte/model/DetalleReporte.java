package com.marketplace.reporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // JPA crea la tabla en la BD automáticamente
@Table(name = "detalle_reporte") // Nombre de la tabla en la BD
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La observación no puede estar vacía")
    @Column(nullable = false, length = 512)
    private String observacion; // Detalle o línea del reporte

    @Column(nullable = false)
    private int valor; // Valor numérico del detalle (ej: cantidad, monto)

    // Relación @ManyToOne: muchos DetalleReporte pertenecen a un solo Reporte
    // @JoinColumn define la columna de llave foránea en la tabla detalle_reporte
    @ManyToOne
    @JoinColumn(name = "reporte_id", nullable = false)
    private Reporte reporte;
}
