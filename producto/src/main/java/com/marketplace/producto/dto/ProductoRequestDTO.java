package com.marketplace.producto.dto;
import lombok.Data;

@Data
public class ProductoRequestDTO {
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private Long vendedorId;
}
