package com.marketplace.vendedor.dto;
import lombok.Data;

@Data
public class VendedorResponseDTO {
    private Long id;
    private String nombreTienda;
    private String descripcion;
    private double reputacion;
    private int cantidadValoraciones;
    private Long usuarioId;
    private boolean activo;
}

