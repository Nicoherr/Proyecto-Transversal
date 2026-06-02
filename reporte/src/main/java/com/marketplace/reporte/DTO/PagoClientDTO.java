package com.marketplace.reporte.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// DTO que representa la respuesta del microservicio de Pago
// Solo los campos necesarios para generar el reporte
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoClientDTO {
    private Long id;
    private String metodoPago;
    private String comprobante;
    private Date fecha;
}