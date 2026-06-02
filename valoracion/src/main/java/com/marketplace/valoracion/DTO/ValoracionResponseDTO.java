package com.marketplace.valoracion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class ValoracionResponseDTO {
    private long id = 0;
    private int numEstrella;
    private String recomendacion;
}
