package com.marketplace.usuario.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    // @NotBlank verifica que no sea null, vacío ("") ni solo espacios ("   ")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    // @Size controla el largo mínimo y máximo del texto
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido, ejemplo: usuario@correo.com")
    // @Email verifica que tenga formato válido: algo@algo.com
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 100, message = "La contraseña debe tener mínimo 8 caracteres")
    // @Size aquí actúa como validador de seguridad básico de contraseña
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9]).+$",
            message = "La contraseña debe tener al menos una mayúscula y un número"
    )
    // @Pattern permite definir reglas complejas con expresiones regulares
    // Este patrón exige mínimo una mayúscula (?=.*[A-Z]) y un número (?=.*[0-9])
    private String password;

    @NotBlank(message = "El rol no puede estar vacío")
    @Pattern(
            regexp = "^(ADMIN|USER|VENDEDOR)$",
            message = "El rol debe ser ADMIN, USER o VENDEDOR"
    )
    // @Pattern aquí asegura que solo entren roles válidos del sistema
    // El ^ y $ significan inicio y fin — o sea que sea exactamente uno de esos tres
    private String rol;
}