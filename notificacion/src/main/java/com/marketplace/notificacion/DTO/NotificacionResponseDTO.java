package com.marketplace.notificacion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class NotificacionResponseDTO {
    private long id = 0;
    private String asunto;
    private String mensaje;
    private Date fecha;
}
