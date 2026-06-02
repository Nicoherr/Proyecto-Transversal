package com.marketplace.valoracion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO que representa la respuesta del microservicio de Producto
// Solo los campos necesarios para validar antes de crear una valoración
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoClientDTO {
    private Long id;
    private String nombre;
    private double precio;
    private int stock;
    private Boolean activo;
}
