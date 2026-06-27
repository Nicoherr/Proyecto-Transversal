package com.marketplace.pago.repository;

import com.marketplace.pago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Chequeo de duplicado eficiente (sin cargar todos los pagos)
    boolean existsByPedidoId(Long pedidoId);

    // Buscar el pago de un pedido específico
    Optional<Pago> findByPedidoId(Long pedidoId);

    // Buscar por método de pago
    List<Pago> findByMetodoPago(String metodoPago);
}