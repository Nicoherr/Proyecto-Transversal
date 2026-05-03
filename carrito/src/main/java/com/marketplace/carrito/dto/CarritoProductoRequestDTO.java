package com.marketplace.carrito.dto;
import lombok.Data;

@Data
public class CarritoProductoRequestDTO {
    private Long carritoId;
    private Long productoId;
    private int cantidad;
}

