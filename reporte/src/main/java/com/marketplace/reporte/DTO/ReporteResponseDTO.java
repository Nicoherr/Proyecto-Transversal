package com.marketplace.reporte.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResponseDTO {
    private long id = 0;
    private Long pagoId;
    private String tipo;
    private String descripcion;
    private Date fecha;
    private Boolean estado;
}
