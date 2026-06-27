package com.marketplace.notificacion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionResponseDTO {
    private long id = 0;
    private Long pedidoId;
    private String asunto;
    private String mensaje;
    private Date fecha;
}
