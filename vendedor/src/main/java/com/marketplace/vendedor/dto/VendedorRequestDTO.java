package com.marketplace.vendedor.dto;
import lombok.Data;

@Data
public class VendedorRequestDTO {
    private String nombreTienda;
    private String descripcion;
    private Long usuarioId;
}
