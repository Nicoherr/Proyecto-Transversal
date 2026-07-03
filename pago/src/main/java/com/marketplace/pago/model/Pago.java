package com.marketplace.pago.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "El id del pedido es obligatorio")
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @NotBlank(message = "Escribe el metodo de pago")
    @Column(nullable = false, length = 50)
    private String metodoPago;

    @Column(nullable = false)
    private String comprobante;

    @Column(nullable = false)
    private Date fecha;




}
