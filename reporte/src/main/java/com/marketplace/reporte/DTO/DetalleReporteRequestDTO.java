package com.marketplace.reporte.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReporteRequestDTO {

    @NotBlank(message = "La observación no puede estar vacía")
    private String observacion;

    @NotNull(message = "El valor no puede ser nulo")
    private int valor;

    @NotNull(message = "El ID del reporte es obligatorio")
    private Long reporteId; // ID del reporte al que pertenece este detalle
}
