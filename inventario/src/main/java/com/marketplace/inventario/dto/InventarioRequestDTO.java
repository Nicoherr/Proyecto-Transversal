package com.marketplace.inventario.dto;

import lombok.Data;

@Data
public class InventarioRequestDTO {
    private Long productoId;
    private int stock;
    private Integer stockMinimo;
}

