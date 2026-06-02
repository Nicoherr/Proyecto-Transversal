package com.marketplace.reporte.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteRequestDTO {

    @NotNull(message = "El id del pago es obligatorio")
    private Long pagoId; // ID del pago asociado (interacción con microservicio Pago)

    @NotBlank(message = "Ingresa un tipo de reporte valido")
    private String tipo; // Debe ser uno de los tipos permitidos (validado en Service)

    @NotBlank(message = "La descripcion del Reporte es obligatoria")
    private String descripcion; // Mínimo 10 caracteres (validado en Service)
}
