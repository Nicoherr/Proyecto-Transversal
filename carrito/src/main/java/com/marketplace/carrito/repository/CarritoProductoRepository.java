package com.marketplace.carrito.repository;

import com.marketplace.carrito.model.CarritoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long> {

    // El doble guión bajo __ le dice a Spring que navegue dentro del objeto Carrito
    // Es decir: busca por carrito.id en vez de carritoId (que ya no existe)
    List<CarritoProducto> findByCarrito_Id(Long carritoId);

    // Mismo principio — navega por carrito.id y además filtra por productoId
    Optional<CarritoProducto> findByCarrito_IdAndProductoId(Long carritoId, Long productoId);
}