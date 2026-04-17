package cl.duoc.store.ms_usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, length = 13, nullable = false)
    @NotNull
    private int run;
    @Column(nullable = false)
    @NotBlank(message = "EL NOMBRE ES OBLIGATORIO")
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message = "EL APELLIDO ES OBLIGATORIO")
    private String apellidos;
    @Column(nullable = true)
    @NotNull()
    private String fechaNac;
    @Column(nullable = false)
    @NotBlank(message = "EL EMAIL ES OBLOGATORIO")
    private String correo;
    @Column(nullable = false)

    private int edad;

}
