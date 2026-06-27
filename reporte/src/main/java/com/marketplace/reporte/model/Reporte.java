package com.marketplace.reporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "El id del pago es obligatorio")
    @Column(nullable = false)
    private Long pagoId;

    @NotBlank(message = "Ingresa un tipo de reporte válido")
    @Column(nullable = false, length = 100)
    private String tipo;

    @NotBlank(message = "La descripción del reporte es obligatoria")
    @Column(nullable = false, length = 512)
    private String descripcion;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private Boolean estado;
}
