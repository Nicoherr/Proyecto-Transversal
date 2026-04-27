package com.marketplace.reporte.DTO;

import java.util.Date;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class ReporteDTO {
    //Devuelve datos al usuario como respuesta
    //Usamos lo mismo de la clase Reporte pero sin la notaciones JPA y sin @Entity y @Table.
    private long id = 0;

    @NotBlank(message = "Ingresa un tipo de reporte valido")
    private String tipo;

    @NotBlank(message = "La descripcion del Reporte es obligatoria")
    private String descripcion;

    //Cuando se genera un reporte el sistema le asigna una fecha en el momento
    private Date fecha;

    //El sistema asigna por defecto el estado
    private Boolean estado;
}
