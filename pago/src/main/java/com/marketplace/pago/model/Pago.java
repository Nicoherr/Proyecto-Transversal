package com.marketplace.pago.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity //JPA crea la tabla en la BD automaticamente.
@Table(name = "pago") //Nombre de la Tabla en la Base de datos
@Data //Getter and Setter
@AllArgsConstructor //Constructores con parametros
@NoArgsConstructor //Constructores sin parametros
public class Pago {
    @Id// este campo es el ID único de cada registro
    @GeneratedValue(strategy = GenerationType.IDENTITY)// el ID se genera solo, no lo ingresa el usuario
    private long id;

    @NotBlank(message = "Escribe el metodo de pago")
    @Column(nullable = false, length = 20)//Validamos que el dato no sea nulo en la Base de Datos.
    private String metodoPago;


    @Column(nullable = false)//Validamos que el dato no sea nulo en la Base de Datos.
    private String comprobante;

    @Column(nullable = false)//Validamos que el dato no sea nulo en la Base de Datos.
    private Date fecha;

}
