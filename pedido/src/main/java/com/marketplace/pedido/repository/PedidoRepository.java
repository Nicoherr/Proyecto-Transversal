package com.marketplace.pedido.repository;

import com.marketplace.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar todos los pedidos de un producto específico
    List<Pedido> findByProductoId(Long productoId);

    // Buscar por nombre de producto
    List<Pedido> findByNomProducto(String nomProducto);

    // Buscar por tipo de producto
    List<Pedido> findByTipoProducto(String tipoProducto);
}