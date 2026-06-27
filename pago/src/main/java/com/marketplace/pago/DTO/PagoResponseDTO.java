package com.marketplace.pago.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoResponseDTO {
    private long id = 0;
    private Long pedidoId;
    private String metodoPago;
    private String comprobante;
    private Date fecha;
}