package com.marketplace.reporte.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class ReporteResponseDTO {
    private long id = 0;
    private String tipo;
    private String descripcion;
    private Date fecha;
    private Boolean estado;
    }
