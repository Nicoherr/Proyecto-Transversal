package com.marketplace.reporte.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReporteResponseDTO {
    private Long id;
    private String observacion;
    private int valor;
    private Long reporteId;   // ID del reporte padre
    private String reporteTipo; // Tipo del reporte padre (para contexto)
}
