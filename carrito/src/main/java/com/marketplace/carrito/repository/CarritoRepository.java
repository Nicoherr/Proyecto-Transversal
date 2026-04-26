package com.marketplace.carrito.repository;
import com.marketplace.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
