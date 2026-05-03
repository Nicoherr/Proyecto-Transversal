package com.marketplace.carrito.dto;
import lombok.Data;

@Data
public class CarritoProductoResponseDTO {
    private Long id;
    private Long carritoId;
    private Long productoId;
    private int cantidad;
}

