package com.marketplace.valoracion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionResponseDTO {
    private long id = 0;
    private Long productoId;
    private int numEstrella;
    private String recomendacion;
}
