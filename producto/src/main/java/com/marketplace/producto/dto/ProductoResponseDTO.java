package com.marketplace.producto.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private Long vendedorId;
    private boolean activo;
}

