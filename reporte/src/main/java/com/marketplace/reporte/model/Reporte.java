package com.marketplace.reporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity //JPA crea la tabla en la BD automaticamente.
@Table(name = "reporte") //Nombre de la Tabla en la Base de datos
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class Reporte {
    @Id// este campo es el ID único de cada registro
    @GeneratedValue(strategy = GenerationType.IDENTITY)// el ID se genera solo, no lo ingresa el usuario
    private long id;

    @NotBlank(message = "Ingresa un tipo de reporte valido")
    @Column(nullable = false, length= 100) //Validamos que el dato no sea nulo en la Base de Datos.
    private String tipo;

    @NotBlank(message = "La descripcion del Reporte es obligatoria")
    @Column(nullable = false, length = 512)//Validamos que el dato no sea nulo en la Base de Datos.
    private String descripcion;

    //Cuando se genera un reporte el sistema le asigna una fecha en el momento
    @Column(nullable = false)//Validamos que el dato no sea nulo en la Base de Datos.
    private Date fecha;

    //El sistema asigna por defecto el estado
    @Column(nullable = false)//Validamos que el dato no sea nulo en la Base de Datos.
    private Boolean estado;

}
