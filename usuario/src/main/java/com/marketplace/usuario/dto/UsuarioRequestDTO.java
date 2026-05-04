package com.marketplace.usuario.dto;
import lombok.Data;
@Data
public class UsuarioRequestDTO {
    private String nombre;
    private String email;
    private String password;
    private String rol;
}

