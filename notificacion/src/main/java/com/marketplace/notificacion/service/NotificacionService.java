package com.marketplace.notificacion.service;

import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.DTO.PedidoClientDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final WebClient pedidoWebClient;

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private NotificacionResponseDTO toDTO(Notificacion n) {
        return new NotificacionResponseDTO(n.getId(), n.getAsunto(), n.getMensaje(), n.getFecha());
    }

    // ─── Comunicación con microservicio Pedido ─────────────────────────────────
    private PedidoClientDTO obtenerPedido(Long pedidoId) {
        log.info("Consultando microservicio Pedido para verificar pedido con id: {}", pedidoId);
        try {
            return pedidoWebClient.get()
                    .uri("/pedidos/{id}", pedidoId)
                    .retrieve()
                    .bodyToMono(PedidoClientDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Pedido con id {} no encontrado en el microservicio de Pedido", pedidoId);
            throw new IllegalArgumentException("No se puede notificar: el pedido con id " + pedidoId + " no existe.");
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de Pedido: {}", e.getMessage());
            throw new IllegalArgumentException("No se pudo verificar el pedido. Intenta nuevamente.");
        }
    }

    // ─── CRUD ─────────────────────────────────────────────────────────────────
    public List<NotificacionResponseDTO> findAllNotificaciones() {
        log.info("Se listan todas las notificaciones");
        return notificacionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public NotificacionResponseDTO findNotificacionesById(long id) {
        log.info("Se busca notificación con id: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada con id: " + id));
        return toDTO(notificacion);
    }

    public NotificacionResponseDTO makeNotificacion(NotificacionRequestDTO dto) {
        log.info("Se inicia la creación de notificación para pedidoId: {}", dto.getPedidoId());

        // ── Regla 1: El asunto no puede tener menos de 5 caracteres ──────────
        if (dto.getAsunto() == null || dto.getAsunto().trim().length() < 5) {
            throw new IllegalArgumentException("El asunto de la notificación debe tener al menos 5 caracteres.");
        }

        // ── Regla 2: El mensaje no puede estar vacío ─────────────────────────
        if (dto.getMensaje() == null || dto.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de la notificación no puede estar vacío.");
        }

        // ── Regla 3: El mensaje no puede superar los 500 caracteres ──────────
        if (dto.getMensaje().trim().length() > 500) {
            throw new IllegalArgumentException("El mensaje no puede superar los 500 caracteres.");
        }

        // ── Interacción: verificar que el pedido existe ───────────────────────
        PedidoClientDTO pedido = obtenerPedido(dto.getPedidoId());

        log.info("Pedido verificado: producto '{}' por ${}. Generando notificación.", pedido.getNomProducto(), pedido.getPrecio());

        // El sistema construye el mensaje enriquecido con datos del pedido real
        String mensajeFinal = dto.getMensaje().trim() + " [Pedido #" + pedido.getId() + " - " + pedido.getNomProducto() + "]";

        Notificacion notificacion = new Notificacion();
        notificacion.setAsunto(dto.getAsunto().trim());
        notificacion.setMensaje(mensajeFinal);
        notificacion.setFecha(new Date());
        notificacion = notificacionRepository.save(notificacion);

        log.info("Notificación creada exitosamente con id: {} para pedido '{}'", notificacion.getId(), pedido.getNomProducto());
        return toDTO(notificacion);
    }

    public void deleteNotificacion(long id) {
        log.info("Se elimina notificación con id: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada con id: " + id));
        notificacionRepository.delete(notificacion);
        log.info("Notificación con id: {} eliminada exitosamente", id);
    }

}


