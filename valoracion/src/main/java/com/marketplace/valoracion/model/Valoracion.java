package com.marketplace.valoracion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //JPA crea la tabla en la BD automaticamente.
@Table(name = "valoracion") //Nombre de la Tabla en la Base de datos
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class Valoracion {
    @Id// este campo es el ID único de cada registro
    @GeneratedValue(strategy = GenerationType.IDENTITY)// el ID se genera solo, no lo ingresa el usuario
    private long id;

    @NotNull(message = "El campo no puede ser nulo")
    @Column(nullable = false)//Validamos que el dato no sea nulo en la Base de Datos.
    private int numEstrella;

    @NotBlank(message = "Ingresa una recomendacion del producto")
    @Column(nullable = false, length = 512)//Validamos que el dato no sea nulo en la Base de Datos.
    private String recomendacion;
}
