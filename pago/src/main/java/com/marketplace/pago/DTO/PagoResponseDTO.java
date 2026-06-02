package com.marketplace.pago.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class PagoResponseDTO {
    private long id = 0;
    private String metodoPago;
    private String comprobante;
    private Date fecha;
}
