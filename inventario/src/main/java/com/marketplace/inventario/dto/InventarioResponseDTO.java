package com.marketplace.inventario.dto;
import lombok.Data;

@Data
public class InventarioResponseDTO {
    private Long id;
    private Long productoId;
    private int stock;
    private int stockMinimo;
}

