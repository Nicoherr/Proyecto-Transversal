package com.marketplace.notificacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    // ID del pedido que originó la notificación
    @NotNull(message = "El id del pedido es obligatorio")
    @Column(nullable = false)
    private Long pedidoId;

    @NotBlank(message = "El asunto debe estar descrito")
    @Column(nullable = false, length = 100)
    private String asunto;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(nullable = false, length = 512)
    private String mensaje;

    @Column(nullable = false)
    private Date fecha;
}
