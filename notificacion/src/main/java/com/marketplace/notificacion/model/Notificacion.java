package com.marketplace.notificacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity //JPA crea la tabla en la BD automaticamente.
@Table(name = "notificacion") //Nombre de la Tabla en la Base de datos
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class Notificacion {
    @Id// este campo es el ID único de cada registro
    @GeneratedValue(strategy = GenerationType.IDENTITY)// el ID se genera solo, no lo ingresa el usuario
    private long id;


    @NotBlank(message = "El asunto deve estar descrito")
    @Column(nullable = false, length = 100)//Validamos que el dato no sea nulo en la Base de Datos.
    private String asunto;


    @NotBlank(message = "El mensaje no puede estar vacio")
    @Column(nullable = false, length = 512)//Validamos que el dato no sea nulo en la Base de Datos.
    private String mensaje;


    @Column(nullable = false)//Validamos que el dato no sea nulo en la Base de Datos.
    private Date fecha;

}
