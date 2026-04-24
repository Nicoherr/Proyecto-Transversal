package com.marketplace.notificacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "notificacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank(message = "El asunto deve estar descrito")
    @Column(nullable = false)
    private String asunto;


    @NotBlank(message = "El mensaje no puede estar vacio")
    @Column(length = 512)
    private String mensaje;


    @Column()
    private Date fecha;

}
