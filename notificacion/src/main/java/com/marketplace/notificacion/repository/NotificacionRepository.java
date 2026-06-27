package com.marketplace.notificacion.repository;

import com.marketplace.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Buscar notificaciones de un pedido específico
    List<Notificacion> findByPedidoId(Long pedidoId);
    // Evitar duplicados: misma notificación (mismo asunto) para el mismo pedido
    boolean existsByPedidoIdAndAsunto(Long pedidoId, String asunto);
    // Buscar por asunto
    List<Notificacion> findByAsunto(String asunto);
    // Buscar por fecha
    List<Notificacion> findByFecha(Date fecha);
    // Buscar por rango de fechas
    List<Notificacion> findByFechaBetween(Date start, Date end);
}
