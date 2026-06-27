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
        return new NotificacionResponseDTO(
                n.getId(),
                n.getPedidoId(),
                n.getAsunto(),
                n.getMensaje(),
                n.getFecha()
        );
    }

    private Notificacion obtenerOFallar(long id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada con id: " + id));
    }

    // ─── Comunicación con microservicio Pedido ─────────────────────────────────
    private PedidoClientDTO obtenerPedido(Long pedidoId) {
        log.info("Consultando microservicio Pedido para id: {}", pedidoId);
        try {
            return pedidoWebClient.get()
                    .uri("/pedidos/{id}", pedidoId)
                    .retrieve()
                    .bodyToMono(PedidoClientDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Pedido con id {} no encontrado", pedidoId);
            throw new IllegalArgumentException(
                    "No se puede notificar: el pedido con id " + pedidoId + " no existe."
            );
        } catch (Exception e) {
            log.error("Microservicio Pedido no disponible: {}", e.getMessage());
            throw new IllegalArgumentException(
                    "El servicio de pedidos no está disponible en este momento. Intenta más tarde."
            );
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
        return toDTO(obtenerOFallar(id));
    }

    public List<NotificacionResponseDTO> findByPedidoId(Long pedidoId) {
        log.info("Se buscan notificaciones del pedido id: {}", pedidoId);
        return notificacionRepository.findByPedidoId(pedidoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public NotificacionResponseDTO makeNotificacion(NotificacionRequestDTO dto) {
        log.info("Creando notificación para pedidoId: {}", dto.getPedidoId());

        // ── Regla 1: El asunto no puede tener menos de 5 caracteres ──────────
        if (dto.getAsunto().trim().length() < 5) {
            throw new IllegalArgumentException("El asunto debe tener al menos 5 caracteres.");
        }

        // ── Regla 2: El mensaje debe tener entre 10 y 500 caracteres ─────────
        if (dto.getMensaje().trim().length() < 10) {
            throw new IllegalArgumentException("El mensaje debe tener al menos 10 caracteres.");
        }
        if (dto.getMensaje().trim().length() > 500) {
            throw new IllegalArgumentException("El mensaje no puede superar los 500 caracteres.");
        }

        // ── Regla 3: No puede existir la misma notificación para ese pedido ──
        if (notificacionRepository.existsByPedidoIdAndAsunto(dto.getPedidoId(), dto.getAsunto().trim())) {
            throw new IllegalArgumentException(
                    "Ya existe una notificación con ese asunto para el pedido id: " + dto.getPedidoId()
            );
        }

        // ── Verifica que el pedido existe ─────────────────────────────────────
        PedidoClientDTO pedido = obtenerPedido(dto.getPedidoId());
        log.info("Pedido '{}' verificado. Creando notificación.", pedido.getNomProducto());

        // Enriquece el mensaje con datos del pedido
        String mensajeFinal = dto.getMensaje().trim()
                + " [Pedido #" + pedido.getId() + " - " + pedido.getNomProducto() + "]";

        Notificacion notificacion = new Notificacion();
        notificacion.setPedidoId(dto.getPedidoId());
        notificacion.setAsunto(dto.getAsunto().trim());
        notificacion.setMensaje(mensajeFinal);
        notificacion.setFecha(new Date());
        notificacion = notificacionRepository.save(notificacion);

        log.info("Notificación creada con id: {}", notificacion.getId());
        return toDTO(notificacion);
    }

    public NotificacionResponseDTO updateNotificacion(long id, NotificacionRequestDTO dto) {
        log.info("Actualizando notificación con id: {}", id);

        // ── Regla 1: El asunto no puede tener menos de 5 caracteres ──────────
        if (dto.getAsunto().trim().length() < 5) {
            throw new IllegalArgumentException("El asunto debe tener al menos 5 caracteres.");
        }

        // ── Regla 2: El mensaje debe tener entre 10 y 500 caracteres ─────────
        if (dto.getMensaje().trim().length() < 10) {
            throw new IllegalArgumentException("El mensaje debe tener al menos 10 caracteres.");
        }
        if (dto.getMensaje().trim().length() > 500) {
            throw new IllegalArgumentException("El mensaje no puede superar los 500 caracteres.");
        }

        Notificacion notificacion = obtenerOFallar(id);
        notificacion.setAsunto(dto.getAsunto().trim());
        notificacion.setMensaje(dto.getMensaje().trim());
        notificacion = notificacionRepository.save(notificacion);

        log.info("Notificación con id: {} actualizada exitosamente", id);
        return toDTO(notificacion);
    }

    public void deleteNotificacion(long id) {
        log.info("Eliminando notificación con id: {}", id);
        notificacionRepository.delete(obtenerOFallar(id));
        log.info("Notificación con id: {} eliminada exitosamente", id);
    }
}



