package com.marketplace.reporte.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReporteRequestDTO {

    @NotBlank(message = "La observación no puede estar vacía")
    @Size(min = 10, max = 512, message = "La observación debe tener entre 10 y 512 caracteres")
    private String observacion;

    @NotNull(message = "El valor no puede ser nulo")
    @Min(value = 1, message = "El valor debe ser mayor a 0")
    private int valor;

    @NotNull(message = "El ID del reporte es obligatorio")
    private Long reporteId;
}

