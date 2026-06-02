package com.marketplace.pago.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Escribe el metodo de pago")
    @Column(nullable = false, length = 50)
    private String metodoPago;

    @Column(nullable = false)
    private String comprobante;

    @Column(nullable = false)
    private Date fecha;

    @Column(name = "pedido_id")
    private Long pedidoId; // Referencia al pedido pagado (para evitar pagos duplicados)
}
