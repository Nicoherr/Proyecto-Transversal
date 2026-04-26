package com.marketplace.carrito.repository;
import com.marketplace.carrito.model.CarritoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long> {

    List<CarritoProducto> findByCarritoId(Long carritoId);

    Optional<CarritoProducto> findByCarritoIdAndProductoId(Long carritoId, Long productoId);
}

