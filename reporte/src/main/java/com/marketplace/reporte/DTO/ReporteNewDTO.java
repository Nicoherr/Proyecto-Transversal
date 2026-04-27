package com.marketplace.reporte.DTO;
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class ReporteNewDTO {
    //Recibe los datos del usuario al crear un Reporte.
    //Usamos los campos de la clase reporte pero solo los que deve llenar un usuario por la Api
    @NotBlank(message = "Ingresa un tipo de reporte valido")
    private String tipo;

    @NotBlank(message = "La descripcion del Reporte es obligatoria")
    private String descripcion;
}
