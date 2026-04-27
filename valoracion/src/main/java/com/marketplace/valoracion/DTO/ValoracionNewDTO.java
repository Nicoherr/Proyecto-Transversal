package com.marketplace.valoracion.DTO;
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class ValoracionNewDTO {
    //Recibe los datos del usuario al crear un Reporte.
    //Usamos los campos de la clase reporte pero solo los que deve llenar un usuario por la Api
    @NotNull(message = "El campo no puede ser nulo")
    private int numEstrella;

    @NotBlank(message = "Ingresa una recomendacion del producto")
    private String recomendacion;
}
